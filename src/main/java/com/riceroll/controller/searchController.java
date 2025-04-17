package com.riceroll.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.riceroll.dto.search.filePageDTO;
import com.riceroll.dto.search.searchDTO;
import com.riceroll.pojo.Article;
import com.riceroll.service.impl.ArticleServiceImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.BeanMapperUtils;
import com.riceroll.vo.search.filePageVO;
import com.riceroll.vo.search.searchVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class searchController {
    @Autowired
    private ArticleServiceImpl articleService;

    @GetMapping("/search")
    public ApiResponse<List<searchVO>> search(@ModelAttribute @Validated searchDTO searchDTO) {
        if(!StringUtils.hasText(searchDTO.getKeyword())){
            List<searchVO> searchVOS = new ArrayList<>();
            return ApiResponse.success(searchVOS);
        }
        Page<Article> page = new Page<>(searchDTO.getPage(), searchDTO.getPage_size());
        List<Article> articles = articleService.selectKeyword(searchDTO.getKeyword(), page);
        List<searchVO> searchVOS = BeanMapperUtils.mapList(articles, searchVO.class);
        return ApiResponse.success(searchVOS,page.getPages());
    }

    @GetMapping("/filePage")
    public ApiResponse<List<filePageVO>> filePage(@ModelAttribute @Validated filePageDTO filePageDTO) {
        try {
            if ("date".equals(filePageDTO.getClassName())) {
                return handleDateSearch(filePageDTO);
            } else if ("tag".equals(filePageDTO.getClassName())) {
                return handleTagSearch(filePageDTO);
            }
            return ApiResponse.fail(400, "排序不存在");
        } catch (Exception e) {
            return ApiResponse.fail(400, "参数错误");
        }
    }

    private ApiResponse<List<filePageVO>> handleDateSearch(filePageDTO dto) {
        int lastYear = Integer.parseInt(dto.getLastData());
        List<Integer> years = articleService.selectYearList().stream()
                .filter(year -> year >= lastYear)
                .toList();

        List<filePageVO> result = new ArrayList<>();
        int count = 0;

        for (Integer year : years) {
            if (count >= dto.getSize()) {
                return ApiResponse.success(result,0L);
            };

            List<Article> articles = articleService.selectAllByCreatedat(year.toString());
            result.add(createFilePageVO(year.toString(), articles));
            count += articles.size();
        }
        return ApiResponse.success(result, 1L);
    }

    private ApiResponse<List<filePageVO>> handleTagSearch(filePageDTO dto) {
        if (StringUtils.hasText(dto.getContent())) {
            List<Article> articles = articleService.selectAllByTag(dto.getContent());
            return ApiResponse.success(List.of(createFilePageVO(dto.getContent(), articles)));
        }

        List<String> tags = articleService.selectTagList();
        if (StringUtils.hasText(dto.getLastData())) {
            tags = tags.subList(tags.indexOf(dto.getLastData()) + 1, tags.size());
        }

        List<filePageVO> result = new ArrayList<>();
        int count = 0;

        for (String tag : tags) {
            if (count >= dto.getSize()) {
                return ApiResponse.success(result,0L);
            };

            List<Article> articles = articleService.selectAllByTag(tag);
            result.add(createFilePageVO(tag, articles));
            count += articles.size();
        }

        return ApiResponse.success(result, 1L);
    }

    private filePageVO createFilePageVO(String label, List<Article> articles) {
        filePageVO vo = new filePageVO();
        vo.setLabel(label);

        if (articles != null && !articles.isEmpty()) {
            List<filePageVO.Content> contents = new ArrayList<>(articles.size());
            for (Article article : articles) {
                filePageVO.Content content = new filePageVO.Content();
                content.setDate(article.getCreatedAt());
                content.setId(article.getId());
                content.setTitle(article.getTitle());
                contents.add(content);
            }
            vo.setContent(contents);
        } else {
            vo.setContent(Collections.emptyList());
        }

        return vo;
    }
}