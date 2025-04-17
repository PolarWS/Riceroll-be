package com.riceroll.vo.article;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class mdVO {
    private Title title;
    private String md;
    private Integer commentRead;
    private Integer wordCount;
    private String date;
    private List<String> tag;
    private List<Map<String, Object>> toc;

    @Data
    @AllArgsConstructor
    public static class Title {
        private String content;
        private String cover;
    }
}
