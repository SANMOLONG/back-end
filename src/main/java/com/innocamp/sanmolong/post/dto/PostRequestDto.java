package com.innocamp.sanmolong.post.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostRequestDto {
    private String nickname;
    private String title;
    private String content;
    private String mountain;
    private String courseNm;
    private String departNm;
    private LocalDate mountDate;
    private int headCount;
}
