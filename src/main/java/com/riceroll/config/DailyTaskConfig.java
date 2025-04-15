package com.riceroll.config;

import com.riceroll.service.impl.MonitoringServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DailyTaskConfig {
    @Autowired
    private MonitoringServiceImpl monitoringService;

    @Scheduled(cron = "0 0 1 * * ?")
    public void executeDailyTask() throws IOException {
        System.out.println("每日定时任务执行中...");
        monitoringService.pingFriendLinks();
    }
}
