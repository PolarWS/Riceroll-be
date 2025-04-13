package com.riceroll.dto.captcha;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class captchaImgDTO {
    @NotBlank(message = "图片令牌不能为空")
    @Size(min = 32, max = 32, message = "图片令牌必须为32字符")
    private String id;
}
