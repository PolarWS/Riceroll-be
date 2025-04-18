package com.riceroll.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.riceroll.dto.comment.commentDTO;
import com.riceroll.mapper.ArticleMapper;
import com.riceroll.pojo.Article;
import com.riceroll.pojo.Comments;
import com.riceroll.mapper.CommentsMapper;
import com.riceroll.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author PolarWS
 * @description 针对表【comments】的数据库操作Service实现
 * @createDate 2025-04-11 15:37:20
 */
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments> implements CommentsService {

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ConfigServiceImpl configService;

    @Value("${email.myEmail}}")
    private String myEmail;

    public List<Comments> getComments(String url, Page<Comments> page) {
        return commentsMapper.selectByUrlOrderByDateDesc(url, page);
    }

    private List<Comments> getCommentsMain(String url, Page<Comments> page) {
        return commentsMapper.selectByUrlAndPidIsNullOrderByDateDesc(url, page);
    }

    public List<Comments> getCommentsByUuid(String uuid) {
        return commentsMapper.selectByUuidOrderByDateDesc(uuid);
    }

    public List<Comments> getCommentsByRid(String rid) {
        return commentsMapper.selectByRidOrderByDateDesc(rid);
    }

    public Boolean commentsLock(String url) throws IOException {
        List<String> segments = Arrays.stream(url.split("/"))
                .filter(s -> !s.isEmpty())
                .toList();

        if (segments.size() >= 2 && "articlePage".equals(segments.get(0))) {
            String articleId = segments.get(1);
            return isNumeric(articleId) && isCommentLocked(articleId);
        } else if (!segments.isEmpty()) {
            return checkNavigationConfig(segments.get(0));
        }
        return false;
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private Boolean isCommentLocked(String articleId) {
        Article article = articleMapper.selectById(articleId);
        return article != null && article.getCommentwrite() == 1;
    }

    private Boolean checkNavigationConfig(String targetId) throws IOException {
        return Optional.ofNullable(configService.getConfig())
                .map(c -> (Map<String, Object>) c.get("navigationBarData"))
                .map(navBar -> (List<Object>) navBar.get("titleData"))
                .orElse(Collections.emptyList())
                .stream()
                .filter(obj -> obj instanceof Map)
                .map(obj -> (Map<?, ?>) obj)
                .filter(map -> targetId.equals(map.get("id")))
                .findFirst()
                .map(map -> (Map<?, ?>) map.get("data"))
                .map(data -> Boolean.TRUE.equals(data.get("comment")))
                .orElse(false);
    }

    public List<Comments> getProcessedComments(commentDTO commentDTO, Page<Comments> commentsPage) {
        // 1. 获取基础的评论列表
        List<Comments> comments = this.getCommentsMain(commentDTO.getUrl(), commentsPage);
        // 2. 如果请求包含锚点评论ID而且页面在1，进行处理
        if (commentDTO.getApc() != null) {
            List<Comments> anchorList = new ArrayList<>();
            if (commentDTO.getPage() < 2) {
                anchorList = this.getCommentsByUuid(commentDTO.getApc());
            }
            if (!anchorList.isEmpty()) {
                comments = mergeComments(comments, anchorList);
            }
        }

        Set<String> uniqueRids = comments.stream()
                .map(Comments::getRid)
                .filter(rid -> !rid.equals(commentDTO.getApc()))
                .collect(Collectors.toSet());
        for (String rid : uniqueRids) {
            List<Comments> commentsByRid = this.getCommentsByRid(rid);
            comments = mergeComments(comments, commentsByRid);
        }

        return comments;
    }

    private List<Comments> mergeComments(List<Comments> list1, List<Comments> list2) {
        return Stream.concat(list1.stream(), list2.stream())
                .distinct()
                .collect(Collectors.toList());
    }

    public String getEmailByPid(String pid) {
        List<Comments> comments = commentsMapper.selectEmailByPid(pid);
        if (!comments.isEmpty()) {
            return comments.get(0).getEmail();
        }else {
            return myEmail;
        }
    }
}






