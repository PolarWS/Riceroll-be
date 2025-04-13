package com.riceroll.vo.sidebar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class tagHotsVO {
    private int hotness;
    private int hots;
    private String tag;
    private int total;
}
