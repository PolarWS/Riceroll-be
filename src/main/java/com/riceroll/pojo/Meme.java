package com.riceroll.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName meme
 */
@TableName(value ="meme")
@Data
public class Meme {
    private Integer id;

    private String url;

    private String name;

    private Integer groupid;
}