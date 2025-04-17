package com.riceroll.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.riceroll.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author PolarWS
* @description 针对表【article】的数据库操作Mapper
* @createDate 2025-04-13 15:08:13
* @Entity com.riceroll.pojo.Article
*/
public interface ArticleMapper extends BaseMapper<Article> {
    Integer selectCount();

    void updateHots(@Param("id") Integer id);

    List<Integer> selectYearList();

    List<String> selectTagList();

    List<Article> selectList(Page<Article> page);

    List<Article> selectById(@Param("id") Integer id);

    List<String> selectTags(@Param("articleId") Integer id);

    List<Article> selectALLByTag(@Param("tag") String tag);

    List<Article> selectAllByCreatedat(@Param("createdat") String createdat);

    List<Article> selectKeyword(@Param("keyword") String keyword,Page<Article> page);
}




