package com.riceroll.controller;

import com.riceroll.dto.article.articlePageDTO;
import com.riceroll.dto.article.mdDTO;
import com.riceroll.utils.ApiResponse;
import com.riceroll.vo.article.articlePageVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class articleController {

    @GetMapping("/articlePage")
    public ApiResponse<articlePageVO> getArticlePage(@ModelAttribute articlePageDTO articlePageDTO) {
        return null;
    }

    @GetMapping("/md")
    public ApiResponse<mdDTO> getMd(@ModelAttribute mdDTO mdDTO) {
        return null;
    }
}
