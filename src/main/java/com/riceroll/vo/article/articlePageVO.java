package com.riceroll.vo.article;

import lombok.Data;

@Data
public class articlePageVO {
    private int id;
    private String title;
    private String content;
    private String cover;
    private String createdAt;
    private Integer wordCount;
}
