package com.riceroll.dto.assets;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class markdownPageDTO {
    @NotNull(message = "markdwon路径不能为空")
    @Size(max = 100,message = "markdwon路径太长")
    private String file;
}
