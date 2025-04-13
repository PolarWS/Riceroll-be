package com.riceroll.controller;

import com.riceroll.service.impl.ConfigServiceImpl;
import com.riceroll.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("config")
public class configController {

    @Autowired
    private ConfigServiceImpl configService;

    @GetMapping
    public ApiResponse getConfig() throws IOException {
        Map<String, Object> config = configService.getConfig();
        if (config == null) {
            return ApiResponse.fail(404, "找不到配置文件");
        }
        return ApiResponse.success(config);
    }
}
