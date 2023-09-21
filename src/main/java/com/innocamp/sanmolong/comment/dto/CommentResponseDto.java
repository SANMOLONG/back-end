package com.innocamp.sanmolong.comment.dto;

import com.innocamp.sanmolong.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String nickname;
    private String content;
    private LocalDate createdAt;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
    }
}
