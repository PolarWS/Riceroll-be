package com.riceroll.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @TableName comments
 */
@TableName(value ="comments")
@Data
public class Comments {
    private String uuid;

    private String name;

    private String link;

    private String ip;

    private String ua;

    private String email;

    private String comment;

    private String rid;

    private String pid;

    private String url;

    private String avatar;

    private String date;

    @TableLogic
    private Integer deleted;
}