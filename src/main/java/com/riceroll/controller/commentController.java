package com.riceroll.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.riceroll.dto.comment.commentDTO;
import com.riceroll.dto.comment.commentaddDTO;
import com.riceroll.pojo.Comments;
import com.riceroll.pojo.Meme;
import com.riceroll.pojo.MemeGroup;
import com.riceroll.service.impl.CommentsServiceImpl;
import com.riceroll.service.impl.EmailServiceImpl;
import com.riceroll.service.impl.MemeServiceImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.BeanMapperUtils;
import com.riceroll.utils.MemoryStoreUtils;
import com.riceroll.vo.comment.commentVO;
import com.riceroll.vo.comment.commentaddVO;
import com.riceroll.vo.comment.memeListVO;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class commentController {

    @Autowired
    private CommentsServiceImpl commentsService;

    @Autowired
    private MemeServiceImpl memeService;

    @Autowired
    private MemoryStoreUtils memoryStore;

    @Autowired
    private EmailServiceImpl emailService;

    @Value("${webUrl}")
    private String webUrl;

    @Value("${email.emailReply}")
    private Boolean emailReply;

    @GetMapping("/comment")
    public ApiResponse<List<commentVO>> getComment(@ModelAttribute @Validated commentDTO commentDTO) {
        Page<Comments> commentsPage = new Page<>(commentDTO.getPage(), commentDTO.getPage_size());
        List<Comments> comments = commentsService.getProcessedComments(commentDTO, commentsPage);
        List<commentVO> commentVOS = BeanMapperUtils.mapList(comments, commentVO.class);
        return ApiResponse.success(commentVOS, commentsPage.getPages());
    }

    @PostMapping("/commentAdd")
    @RateLimiter(name = "commentAddService")
    public ApiResponse<commentaddVO> addComment(@RequestBody @Validated commentaddDTO commentaddDTO, HttpServletRequest request) throws IOException {
        if (memoryStore.get("captchaPassID:" + commentaddDTO.getPassId()) == null) {
            return ApiResponse.fail(400, "验证码错误");
        }

        if (!commentsService.commentsLock(commentaddDTO.getUrl())) {
            return ApiResponse.fail(400, "评论已锁定");
        }

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");  // 获取User-Agent
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        String currentTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Comments comments = BeanMapperUtils.map(commentaddDTO, Comments.class);
        comments.setIp(ipAddress);
        comments.setUa(userAgent);
        comments.setUuid(uuid);
        if (commentaddDTO.getRid().isEmpty()) {
            comments.setRid(uuid);
        } else {
            comments.setRid(commentaddDTO.getRid());
        }
        if (!commentaddDTO.getPid().isEmpty()) {
            comments.setPid(commentaddDTO.getPid());
        }
        comments.setDate(currentTime);
        comments.setDeleted(0);
        boolean saveComment = commentsService.saveOrUpdate(comments);
        if (saveComment) {
            if (emailReply) {
                try {
                    emailService.sendHtmlEmail(
                            commentsService.getEmailByPid(commentaddDTO.getPid()),
                            commentaddDTO.getName(),
                            commentaddDTO.getComment(),
                            webUrl + commentaddDTO.getUrl() + "#" + uuid);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            commentaddVO commentaddVOS = BeanMapperUtils.map(comments, commentaddVO.class);
            return ApiResponse.success(commentaddVOS);
        }
        return ApiResponse.fail(400, "评论添加失败");
    }

    @GetMapping("/memeList")
    public ApiResponse<List<memeListVO>> getMemeList() {
        List<MemeGroup> memeGroups = memeService.getMemeGroups();
        List<memeListVO> memeListVO = BeanMapperUtils.mapList(memeGroups, memeListVO.class);
        for (memeListVO memeListVO1 : memeListVO) {
            List<Meme> meme = memeService.getMemes(memeListVO1.getId());
            memeListVO1.setMemes(BeanMapperUtils.mapList(meme, memeListVO.Meme.class));
        }
        return ApiResponse.success(memeListVO);
    }

}
