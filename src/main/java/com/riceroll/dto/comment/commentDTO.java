package com.riceroll.dto.comment;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class commentDTO {
    @NotNull(message = "文章路径不能为空")
    @Size(max = 500, message = "文章路径长度不能超过500字符")
    private String url;

    @Pattern(regexp = "^(|[0-9a-fA-F]{32})$", message = "UUID格式错误")
    private String apc;

    @Min(value = 1, message = "页码必须大于等于1")
    private Integer page = 1;

    @Min(value = 1, message = "每页大小必须大于等于1")
    @Max(value = 100, message = "每页大小不能超过100")
    private Integer page_size = 20;
}
