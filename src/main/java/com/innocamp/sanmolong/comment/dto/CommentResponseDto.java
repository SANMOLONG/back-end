package com.innocamp.sanmolong.comment.dto;

import com.innocamp.sanmolong.comment.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long commentId;
    private String nickname;
    private String content;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.nickname = comment.getUser().getNickname();
        this.content = comment.getContent();

    }
}
