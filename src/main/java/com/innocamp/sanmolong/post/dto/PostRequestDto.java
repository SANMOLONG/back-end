package com.innocamp.sanmolong.post.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PostRequestDto {
    private String nickname;
    private String title;
    private String content;
    private String mountain;
    private String course;
    private LocalDate mountDate;
}
