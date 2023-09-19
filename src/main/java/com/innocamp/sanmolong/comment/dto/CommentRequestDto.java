package com.innocamp.sanmolong.comment.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private Long postId;
    private String nickname;
    private String content;
}
