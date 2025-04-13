package com.riceroll.dto.assets;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class imageDTO {
    @NotNull(message = "图片路径不能为空")
    @Max(value = 100,message = "图片路径太长")
    private String filename;
}
