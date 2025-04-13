package com.riceroll.dto.captcha;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Data
public class captchaCheckDTO {
    @NotNull(message = "验证码会话令牌不能为空")
    @Size(min = 32, max = 32, message = "验证码会话令牌必须为32字符")
    private String id;

    @NotNull(message = "旋转角度不能为空")
    private Double rotation;

    @NotNull(message = "鼠标轨迹数据不能为空")
    @Size(min = 1, max = 3000, message = "鼠标轨迹数据太大了喵~")
    private List<Long[]> mouseData;

}
