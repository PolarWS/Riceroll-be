package com.riceroll.service.impl;

import com.riceroll.mapper.ArticleMapper;
import com.riceroll.mapper.CommentsMapper;
import com.riceroll.mapper.TagsMapper;
import com.riceroll.pojo.Comments;
import com.riceroll.pojo.Tags;
import com.riceroll.service.SidebarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SidebarServiceImpl implements SidebarService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private TagsMapper tagsMapper;


    public Integer ArticleCount() {
        return articleMapper.selectCount();
    }

    public Integer CommentCount() {
        return commentsMapper.selectCount();
    }

    public List<Comments> selectCommentsNew(){
        return commentsMapper.selectCommentsNew();
    }

    public List<Tags> selectTagsHot(){
        return tagsMapper.selectHots();
    }
}
