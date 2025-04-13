package com.riceroll.dto.search;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class filePageDTO {
    @JsonProperty("class")
    private String className = "date";

    @Max(value = 50,message = "标签太长了喵~")
    private String content;

    @Size(min = 1000, max = 10000,message = "超出电路板世纪啦喵~")
    private Integer lastData;

    @Size(min = 1,max = 40,message = "查询调数太多了喵~")
    private Integer size = 30;
}
