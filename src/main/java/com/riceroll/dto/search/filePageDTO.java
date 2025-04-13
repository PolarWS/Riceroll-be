package com.riceroll.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class filePageDTO {
    private String className = "date";

    @Size(max = 50, message = "标签太长了喵~")
    private String content;

    @Size(max = 100, message = "超出范围啦喵~")
    private String lastData;

    @Min(value = 1, message = "查询调数太少了喵~")
    @Max(value = 40, message = "查询调数太多了喵~")
    private Integer size = 30;
}
