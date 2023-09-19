package com.innocamp.sanmolong.post.dto;

import com.innocamp.sanmolong.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TotalPostResponseDto {
    private List<PostDto> uncompleted = new ArrayList<>();
    private List<PostDto> completed = new ArrayList<>();

    public TotalPostResponseDto(List<Post> posts) {
        posts.forEach(post -> {
            if (post.getCompleted()) {
                completed.add(new PostDto(post));
            } else {
                uncompleted.add(new PostDto(post));
            }
        });
    }

    @Getter
    @NoArgsConstructor
    static class PostDto {
        private String title;
        private String course;
        private String level;
        private LocalDate mountDate;
        private int count;

        public PostDto(Post post) {
            this.title = post.getTitle();
            this.course = post.getMountain().getCourse();
            this.level = post.getMountain().getCourseLevel();
            this.mountDate = post.getMountDate();
            this.count = post.getTogethers().size();
        }
    }
}
