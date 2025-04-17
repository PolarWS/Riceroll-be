package com.riceroll.config;

import com.riceroll.service.MonitoringService;
import com.riceroll.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class InitLoaderConfig implements CommandLineRunner {

    @Autowired
    private ConfigServiceImpl configService;

    @Autowired
    private CaptchaServiceImpl captchaService;

    @Autowired
    private AssetsServiceImpl assetsServiceImpl;

    @Autowired
    private MonitoringServiceImpl monitoringService;

    @Autowired
    private EmailServiceImpl emailService;

    @Override
    public void run(String... args) throws Exception {
        configService.getConfig();
        captchaService.saveCaptcha();
        assetsServiceImpl.saveMarkdown();
        emailService.saveEmail();
        monitoringService.pingFriendLinks();
    }
}
