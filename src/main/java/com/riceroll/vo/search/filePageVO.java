package com.riceroll.vo.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class filePageVO {
    private List<Content> content;
    private String label;

    @Data
    private static class Content{
        private String date;
        private int id;
        private String title;
    }
}
