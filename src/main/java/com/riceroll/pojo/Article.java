package com.riceroll.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

/**
 * @TableName article
 */
@TableName(value ="article")
@Data
public class Article {
    private Integer id;

    private String title;

    private String createdAt;

    private String updatedAt;

    private Integer wordCount;

    private String cover;

    private Integer hot;

    private Integer like;

    private String content;

    private String toc;

    private Integer articleLock;

    private Integer toclock;

    private Integer top;

    private Integer commentread;

    private Integer commentwrite;

    @TableLogic
    private Integer deleted;
}