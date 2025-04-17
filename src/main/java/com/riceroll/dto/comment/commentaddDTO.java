package com.riceroll.dto.comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class commentaddDTO {
    @Size(max = 500, message = "链接长度不能超过500字符")
    @Pattern(regexp = "^(|(https?://)?([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?)$", message = "链接格式不正确")
    private String link;

    @NotBlank(message = "名称不能为空")
    @Size(max = 100, message = "名称长度不能超过100字符")
    private String name;

    @Email(message = "邮箱格式不正确")
    @NotBlank(message = "邮箱不能为空")
    @Size(max = 100, message = "邮箱长度不能超过100字符")
    private String email;

    @Pattern(regexp = "^(|[0-9a-fA-F]{32})$", message = "楼层主ID格式错误")
    private String rid;

    @Pattern(regexp = "^(|[0-9a-fA-F]{32})$", message = "回复ID格式错误")
    private String pid;

    @NotBlank(message = "当前页面URL不能为空")
    @Size(max = 500, message = "当前页面URL长度不能超过500字符")
    private String url;

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 500, message = "评论内容长度不能超过500字符")
    private String comment;

    @NotBlank(message = "验证通行令牌不能为空")
    @Pattern(regexp = "^[0-9a-fA-F]{32}$", message = "验证通行令牌格式错误")
    private String passId;
}
