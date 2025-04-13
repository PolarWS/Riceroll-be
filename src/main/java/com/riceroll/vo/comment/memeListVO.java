package com.riceroll.vo.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class memeListVO {
    private int id;
    private List<Meme> meme;
    private String name;
    private String url;

    @Data
    public static class Meme {
        private int id;
        private String name;
        private String url;
    }
}
