package com.riceroll.config;

import com.riceroll.service.impl.AssetsImpl;
import com.riceroll.service.impl.CaptchaServiceImpl;
import com.riceroll.service.impl.ConfigServiceImpl;
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
    private AssetsImpl assetsImpl;

    @Override
    public void run(String... args) throws Exception {
        configService.getConfig();
        captchaService.saveCaptcha();
        assetsImpl.saveMarkdown();
    }
}
