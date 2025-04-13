package com.riceroll.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.riceroll.dto.article.articlePageDTO;
import com.riceroll.dto.article.mdDTO;
import com.riceroll.pojo.Article;
import com.riceroll.service.impl.ArticleServiceImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.BeanMapperUtils;
import com.riceroll.vo.article.articlePageVO;
import com.riceroll.vo.article.mdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class articleController {
    @Autowired
    ArticleServiceImpl articleService;

    @GetMapping("/articlePage")
    public ApiResponse<List<articlePageVO>> getArticlePage(@ModelAttribute @Validated articlePageDTO articlePageDTO) {
        Page<Article> page = new Page<>(articlePageDTO.getPage(), articlePageDTO.getPage_size());
        List<Article> articles = articleService.selectList(page);
        List<articlePageVO> articlePageVOS = BeanMapperUtils.mapList(articles, articlePageVO.class);
        return ApiResponse.success(articlePageVOS);
    }

    @GetMapping("/md")
    public ApiResponse<mdVO> getMd(@ModelAttribute @Validated mdDTO mdDTO) {
        Article article = articleService.selectById(mdDTO.getId());
        if (article != null) {
            mdVO mdVO = new mdVO();
            mdVO.setTitle(BeanMapperUtils.map(article, mdVO.Title.class));
            mdVO.setMd(article.getContent());
            mdVO.setTag(articleService.selectTags(mdDTO.getId()));
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                List<Map<String, Object>> tocList = objectMapper.readValue(
                        article.getToc(),
                        new TypeReference<List<Map<String, Object>>>() {
                        }
                );
                mdVO.setToc(tocList);
            } catch (Exception e) {
                mdVO.setToc(Collections.emptyList());
            }
            return ApiResponse.success(mdVO);
        }
        return ApiResponse.fail(404, "文章不存在");
    }
}
