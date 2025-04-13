package com.riceroll.dto.article;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class mdDTO {
    @NotNull(message = "文章ID不能为空")
    @Min(value = 1,message = "文章ID必须大于等于1")
    private Integer id;
}
