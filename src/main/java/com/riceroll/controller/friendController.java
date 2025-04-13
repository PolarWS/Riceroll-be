package com.riceroll.controller;

import com.riceroll.utils.ApiResponse;
import com.riceroll.vo.friend.friendLinkPageVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class friendController {
    @GetMapping("/friendLinkPage")
    public ApiResponse<friendLinkPageVO> friendLinkPage() {
        return null;
    }
}
