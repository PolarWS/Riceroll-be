package com.riceroll.dto.comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class commentaddDTO {
    @Size(max = 500, message = "链接长度不能超过500字符")
    private String link;

    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称长度不能超过100字符")
    private String name;

    @Email(message = "邮箱格式不正确")
    @Size(max = 100, message = "邮箱长度不能超过100字符")
    private String email;

    @Size(min = 32, max = 32, message = "楼层主ID必须为32字符")
    private String rid;

    @Size(min = 32, max = 32, message = "回复ID必须为32字符")
    private String pid;

    @NotBlank(message = "当前页面URL不能为空")
    @Size(max = 500, message = "当前页面URL长度不能超过500字符")
    private String url;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 500, message = "评论内容长度不能超过500字符")
    private String comment;

    @NotBlank(message = "验证通行令牌不能为空")
    @Size(min = 32, max = 32, message = "验证通行令牌必须为32字符")
    private String passId;
}
