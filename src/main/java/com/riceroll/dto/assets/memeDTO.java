package com.riceroll.dto.assets;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class memeDTO {
    @NotNull(message = "meme路径不能为空")
    @Max(value = 100,message = "meme路径太长")
    private String pathname;

    @NotNull(message = "meme文件名不能为空")
    @Max(value = 100,message = "meme文件名太长")
    private String filename;
}
