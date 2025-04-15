package com.riceroll.controller;

import com.riceroll.service.impl.SidebarServiceImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.BeanMapperUtils;
import com.riceroll.vo.sidebar.blogTotalVO;
import com.riceroll.vo.sidebar.commentNewVO;
import com.riceroll.vo.sidebar.tagHotsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class sidebarController {

    @Autowired
    private SidebarServiceImpl sidebarService;

    @GetMapping("/blogTotal")
    public ApiResponse<blogTotalVO> getBlogTotal() {
        blogTotalVO blogTotalVO = new blogTotalVO(sidebarService.ArticleCount(),sidebarService.CommentCount());
        return ApiResponse.success(blogTotalVO);
    }

    @GetMapping("/commentNew")
    public ApiResponse<List<commentNewVO>> getCommentNew() {
        List<commentNewVO> commentNewVOS = BeanMapperUtils.mapList(sidebarService.selectCommentsNew(), commentNewVO.class);
        return ApiResponse.success(commentNewVOS);
    }

    @GetMapping("/tagHots")
    public ApiResponse<List<tagHotsVO>> getTagHots() {
        List<tagHotsVO> tagHotsVOS = BeanMapperUtils.mapList(sidebarService.selectTagsHot(), tagHotsVO.class);
        return ApiResponse.success(tagHotsVOS);
    }
}
