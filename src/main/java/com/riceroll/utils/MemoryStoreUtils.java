package com.riceroll.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class MemoryStoreUtils {
    /**
     * 类似于redis的内存存储工具类
     * 存储键值对的Map
     * key: 键
     * value: 值
     * expireMap: 存储键的过期时间
     * scheduler: 定时任务调度器
     */
    private final Map<String, Object> store = new ConcurrentHashMap<>();
    private final Map<String, Long> expireMap = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public void set(String key, Object value) {
        store.put(key, value);
        expireMap.remove(key);
    }

    public void set(String key, Object value, long ttlMillis) {
        store.put(key, value);
        expireMap.put(key, System.currentTimeMillis() + ttlMillis);
    }

    public Object get(String key) {
        if (isExpired(key)) {
            delete(key);
            return null;
        }
        return store.get(key);
    }

    public void delete(String key) {
        store.remove(key);
        expireMap.remove(key);
    }

    public boolean exists(String key) {
        if (isExpired(key)) {
            delete(key);
            return false;
        }
        return store.containsKey(key);
    }

    public List<Object> fuzzySearch(String pattern) {
        List<Object> result = new ArrayList<>();
        for (Map.Entry<String, Object> entry : store.entrySet()) {
            String key = entry.getKey();
            if (!isExpired(key) && key.contains(pattern)) {
                result.add(entry.getValue());
            }
        }
        return result;
    }


    private boolean isExpired(String key) {
        Long expireTime = expireMap.get(key);
        return expireTime != null && System.currentTimeMillis() > expireTime;
    }

    @PostConstruct
    public void startCleanupTask() {
        scheduler.scheduleAtFixedRate(() -> {
            long now = System.currentTimeMillis();
            for (Map.Entry<String, Long> entry : expireMap.entrySet()) {
                if (entry.getValue() < now) {
                    String key = entry.getKey();
                    store.remove(key);
                    expireMap.remove(key);
                }
            }
        }, 1, 1, TimeUnit.SECONDS);
    }
}
