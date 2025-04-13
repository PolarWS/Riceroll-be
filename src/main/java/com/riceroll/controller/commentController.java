package com.riceroll.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.riceroll.dto.comment.commentDTO;
import com.riceroll.dto.comment.commentaddDTO;
import com.riceroll.pojo.Comments;
import com.riceroll.pojo.Meme;
import com.riceroll.pojo.MemeGroup;
import com.riceroll.service.impl.CommentsServiceImpl;
import com.riceroll.service.impl.MemeServiceImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.BeanMapperUtils;
import com.riceroll.utils.MemoryStoreUtils;
import com.riceroll.vo.comment.commentVO;
import com.riceroll.vo.comment.commentaddVO;
import com.riceroll.vo.comment.memeListVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class commentController {
    @Autowired
    private CommentsServiceImpl commentsService;

    @Autowired
    private MemeServiceImpl memeService;

    @Autowired
    private MemoryStoreUtils memoryStore;

    @GetMapping("/comment")
    public ApiResponse<List<commentVO>> getComment(@ModelAttribute @Validated commentDTO commentDTO) {
        // 获取分页信息
        Page<Comments> commentsPage = new Page<>(commentDTO.getPage(), commentDTO.getPage_size());
        // 调用 Service 层业务方法获取处理后的评论列表
        List<Comments> comments = commentsService.getProcessedComments(commentDTO, commentsPage);
        List<commentVO> commentVOS = BeanMapperUtils.mapList(comments, commentVO.class);
        return ApiResponse.success(commentVOS, commentsPage.getPages());
    }

    @PostMapping("/commentadd")
    public ApiResponse<commentaddVO> addComment(@RequestBody @Validated commentaddDTO commentaddDTO, HttpServletRequest request) {
        if(memoryStore.get(commentaddDTO.getPassId()) == null){
            return ApiResponse.fail(400, "验证码错误");
        }

        //验证路径在不在config文件或者评论区有没有锁定，后面再回来写咯
        String ipAddress = request.getRemoteAddr();
        String uuid = java.util.UUID.randomUUID().toString().replace("-", "");
        String currentTime = java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Comments comments = BeanMapperUtils.map(commentaddDTO, Comments.class);
        comments.setIp(ipAddress);
        comments.setUuid(uuid);
        comments.setDate(currentTime);

        boolean saveComment = commentsService.saveOrUpdate(comments);
        if (saveComment) {
            commentaddVO commentaddVOS = BeanMapperUtils.map(comments,commentaddVO.class);
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
            memeListVO1.setMeme(BeanMapperUtils.mapList(meme, memeListVO.Meme.class));
        }
        return ApiResponse.success(memeListVO);
    }

}
