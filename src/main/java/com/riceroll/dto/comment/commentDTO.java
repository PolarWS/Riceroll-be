package com.riceroll.dto.comment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class commentDTO {
    @NotNull(message = "文章路径不能为空")
    @Size(max = 500, message = "文章路径长度不能超过500字符")
    private String url;

    @Size(max = 32,min = 32,message = "请输入正确的UUID")
    private String apc;

    @Min(value = 1, message = "页码必须大于等于1")
    private Integer page = 1;

    @Min(value = 1, message = "每页大小必须大于等于1")
    @Max(value = 100, message = "每页大小不能超过100")
    private Integer page_size = 20;
}
