package com.riceroll.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.riceroll.pojo.Article;
import com.riceroll.service.ArticleService;
import com.riceroll.mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author PolarWS
* @description 针对表【article】的数据库操作Service实现
* @createDate 2025-04-13 15:08:13
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{
    @Autowired
    private ArticleMapper articleMapper;

    public List<Article> selectList(Page<Article> page) {
        return articleMapper.selectList(page);
    }

    public Article selectById(Integer id) {
        List<Article> articles = articleMapper.selectById(id);
        if(articles.size() > 0){
            return articles.get(0);
        }
        return null;
    }

    public List<String> selectTags(Integer id) {
        return articleMapper.selectTags(id);
    }

    public List<Article> selectKeyword(String keyword,Page<Article> page) {
        List<Article> articles = articleMapper.selectKeyword('%' + keyword + '%', page);
        return articles;
    }

    public List<Integer> selectYearList(){
        return articleMapper.selectYearList();
    }

    public List<Article> selectAllByCreatedat(String year){
        return articleMapper.selectAllByCreatedat(year + '%');
    }

    public List<String> selectTagList(){
        return articleMapper.selectTagList();
    }

    public List<Article> selectAllByTag(String tag){
        return articleMapper.selectALLByTag(tag);
    }
}




