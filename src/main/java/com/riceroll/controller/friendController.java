package com.riceroll.controller;

import com.riceroll.service.impl.MonitoringServiceImpl;
import com.riceroll.utils.ApiResponse;
import com.riceroll.vo.friend.friendLinkPageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class friendController {
    @Autowired
    private MonitoringServiceImpl monitoringService;

    @GetMapping("/friendLinkPage")
    public ApiResponse<List<friendLinkPageVO>> friendLinkPage() {
        Map<String, Long> friendLinksStatus = monitoringService.getFriendLinksStatus();
        List<friendLinkPageVO> friendLinkPageVOList = new ArrayList<>();
        for (Map.Entry<String, Long> entry : friendLinksStatus.entrySet()) {
            friendLinkPageVO friendLinkPageVO = new friendLinkPageVO();
            System.out.println("entry = " + entry);
            friendLinkPageVO.setHash(entry.getKey());
            friendLinkPageVO.setPing(entry.getValue());
            friendLinkPageVOList.add(friendLinkPageVO);
        }
        return ApiResponse.success(friendLinkPageVOList);
    }
}
