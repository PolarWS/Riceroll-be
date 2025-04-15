package com.riceroll.pojo;

import lombok.Data;

/**
 * @TableName tags
 */
@Data
public class Tags {
    private Integer id;

    private String tag;

    private Integer total;

    private Integer hots;

    private Integer like;

    private Integer hotness;
}