package com.riceroll.service.impl;

import com.riceroll.utils.MemoryStoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class MonitoringServiceImpl {
    @Autowired
    ConfigServiceImpl configService;

    @Autowired
    private MemoryStoreUtils memoryStoreUtils;

    public void pingFriendLinks() throws IOException {
        List<Object> cardList = null;
        try {
            cardList = Optional.ofNullable(configService.getConfig())
                    .map(c -> (Map<String, Object>) c.get("navigationBarData"))
                    .map(c -> (List<Object>) c.get("titleData"))
                    .map(list -> list.stream()
                            .filter(item -> {
                                Map<String, Object> mapItem = (Map<String, Object>) item;
                                return "friendLinkPage".equals(mapItem.get("id"));
                            })
                            .findFirst()
                            .map(item -> (Map<String, Object>) ((Map<String, Object>) item).get("data"))
                            .map(data -> (List<Object>) data.get("cardList"))
                            .orElse(Collections.emptyList()))
                    .orElse(Collections.emptyList());
        } catch (Exception e) {
            System.out.println("读取配置文件失败无法获取友链: " + e.getMessage());
            e.printStackTrace();
        }

        //创建自定义线程池控制并发数
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<CompletableFuture<Void>> futures = cardList.stream()
                .map(card -> CompletableFuture.runAsync(() -> {
                    String url = (String) ((Map<String, Object>) card).get("url");
                    try {
                        long startTime = System.currentTimeMillis();
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(url))
                                .method("HEAD", HttpRequest.BodyPublishers.noBody())
                                .build();
                        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());
                        long latency = System.currentTimeMillis() - startTime;
                        System.out.println("URL: " + url + " 状态: " + response.statusCode() + " 延迟: " + latency + "ms");
                        if (response.statusCode() == 200) {
                            memoryStoreUtils.set("ping:" + Integer.toHexString(url.hashCode()), latency);
                        }
                    } catch (Exception e) {
                        System.out.println("URL: " + url + " 请求失败: " + e.getMessage());
                    }
                }, executor))
                .collect(Collectors.toList());

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        executor.shutdown();
    }

    public Map<String, Long> getFriendLinksStatus() {
        Map<String, Object> objects = memoryStoreUtils.fuzzySearchMap("ping:");
        if (objects == null || objects.isEmpty()) {
            return Collections.emptyMap();
        }

        return objects.entrySet().stream()
            .filter(entry -> entry.getValue() instanceof Long)
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                entry -> (Long) entry.getValue()
            ));
    }
}
