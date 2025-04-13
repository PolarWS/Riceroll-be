package com.riceroll.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName captcha
 */
@TableName(value ="captcha")
@Data
public class Captcha {
    private String id;

    private String url;

    private Integer rotation;
}