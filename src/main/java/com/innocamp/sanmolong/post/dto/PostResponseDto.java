package com.innocamp.sanmolong.post.dto;

import com.innocamp.sanmolong.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class PostResponseDto {
    private Long postId;
    private String title;
    private String mountain;
    private String course;
    private String content;
    private LocalDate mountDate;
    private boolean completed;
    private String author;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.mountain = post.getMountain().getMountain();
        this.course = post.getMountain().getCourse();
        this.content = post.getContent();
        this.mountDate = post.getMountDate();
        this.completed = post.getCompleted();
        this.author = post.getUser().getNickname();
    }
}
