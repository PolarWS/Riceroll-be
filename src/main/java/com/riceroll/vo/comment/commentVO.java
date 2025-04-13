package com.riceroll.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class commentVO {
    private String date;
    private String name;
    private String comment;
    private String avatar;
    private String email;
    private String link;
    private String pid;
    private String rid;
    private String uuid;
    private String ua;
}
