package com.riceroll.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FileValidationUtils {

    // 允许的文件扩展名（小写）
    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>(Arrays.asList(
            ".jpg", ".jpeg", ".png", ".gif", ".webp", ".md"
    ));

    /**
     * 校验文件扩展名是否合法（支持一个点）
     *
     * @param filename 文件名
     * @return true表示合法扩展名，false表示非法
     */
    public static boolean isValidFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return false;
        }

        int lastDot = filename.lastIndexOf('.');
        if (lastDot <= 0 || lastDot == filename.length() - 1) {
            return false;
        }

        String extension = filename.substring(lastDot).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(extension);
    }
}
