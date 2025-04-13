package com.riceroll.dto.assets;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class imageDTO {
    @NotNull(message = "图片路径不能为空")
    @Size(max = 100,message = "图片路径太长")
    private String filename;
}
