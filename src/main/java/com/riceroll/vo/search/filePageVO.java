package com.riceroll.vo.search;

import lombok.Data;
import java.util.List;

@Data
public class filePageVO {
    private List<Content> content;
    private String label;

    @Data
    public static class Content{
        private String date;
        private int id;
        private String title;
    }
}
