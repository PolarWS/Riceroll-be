package com.riceroll.controller;

import com.riceroll.dto.search.filePageDTO;
import com.riceroll.dto.search.searchDTO;
import com.riceroll.utils.ApiResponse;
import com.riceroll.vo.search.filePageVO;
import com.riceroll.vo.search.searchVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class searchController {
    @GetMapping("/search")
    public ApiResponse<searchVO> search(@ModelAttribute searchDTO searchDTO){
        return null;
    }

    @GetMapping("/filePage")
    public ApiResponse<filePageVO> filePage(@ModelAttribute filePageDTO filePageDTO){
        return null;
    }
}
