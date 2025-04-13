package com.riceroll.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName memeGroup
 */
@TableName(value ="memeGroup")
@Data
public class MemeGroup {
    private Integer id;

    private String url;

    private String name;

    private String note;
}