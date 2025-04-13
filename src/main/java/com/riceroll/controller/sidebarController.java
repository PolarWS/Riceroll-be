package com.riceroll.controller;

import com.riceroll.utils.ApiResponse;
import com.riceroll.vo.sidebar.blogTotalVO;
import com.riceroll.vo.sidebar.commentNewVO;
import com.riceroll.vo.sidebar.tagHotsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sidebarController {
    @GetMapping("/blogTotal")
    public ApiResponse<blogTotalVO> getBlogTotal() {
        return null;
    }

    @GetMapping("/commentNew")
    public ApiResponse<commentNewVO> getCommentNew() {
        return null;
    }

    @GetMapping("/tagHots")
    public ApiResponse<tagHotsVO> getTagHots() {
        return null;
    }
}
