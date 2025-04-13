package com.riceroll.vo.article;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class mdVO {
    private Title title;
    private String md;
    private List<String> tag;
    private List<Toc> toc;

    @Data
    private static class Title {
        private String content;
        private String img;
    }

    @Data
    private static class Toc {
        private int level;
        private String text;
    }
}
