package com.riceroll.service.impl;

import com.riceroll.utils.ApiResponse;
import com.riceroll.utils.MemoryStoreUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class AssetsImpl {

    @Value("${static.rootpath}")
    private String rootPath;

    @Autowired
    private MemoryStoreUtils memoryStoreUtils;

    public String getMarkdownPage(String filename) {
        String hexHash = Integer.toHexString(filename.hashCode());
        if (memoryStoreUtils.exists("markdown:" + hexHash)) {
            return (String) memoryStoreUtils.get("markdown:" + hexHash);
        }
        Path path = Paths.get(rootPath, "markdownPage", filename).normalize();
        Path target = path.resolve(filename).normalize();
        if (!target.startsWith(path)) {
            return null;
        }
        try {
            return Files.readString(path);
        } catch (IOException e) {
            return null;
        }
    }

    public void saveMarkdown() {
        Path path = Paths.get(rootPath, "markdownPage");
        try (Stream<Path> paths = Files.walk(path)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".md"))
                    .forEach(mdFile -> {
                        try {
                            String content = Files.readString(mdFile);
                            String fileName = mdFile.getFileName().toString();
                            String hexHash = Integer.toHexString(fileName.hashCode());
                            memoryStoreUtils.set("markdown:" + hexHash, content);
                        } catch (IOException e) {
                            System.err.println("读取文件失败: " + mdFile);
                        }
                    });
        } catch (IOException e) {
            System.err.println("扫描目录失败: " + path);
        }
    }
}
