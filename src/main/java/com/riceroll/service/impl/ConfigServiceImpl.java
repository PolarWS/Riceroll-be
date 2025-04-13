package com.riceroll.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.riceroll.service.ConfigService;
import com.riceroll.utils.MemoryStoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class ConfigServiceImpl implements ConfigService {
    @Value("${static.rootpath}")
    private String rootpath;

    @Autowired
    private MemoryStoreUtils memoryStore;

    @Override
    public Map<String, Object> getConfig() throws IOException {
        Object config = memoryStore.get("config");

        if (config instanceof Map) {
            return (Map<String, Object>) config;
        }

        String filePath = rootpath + File.separator + "config.json";
        File configFile = new File(filePath);

        if (!configFile.exists()) {
            return null;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> configData = objectMapper.readValue(configFile, Map.class);
        memoryStore.set("config", configData);
        return configData;
    }
}
