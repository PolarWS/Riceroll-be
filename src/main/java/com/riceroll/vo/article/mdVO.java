package com.riceroll.vo.article;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class mdVO {
    private Title title;
    private String md;
    private List<String> tag;
    private List<Map<String, Object>> toc;

    @Data
    public static class Title {
        private String content;
        private String cover;
    }
}
