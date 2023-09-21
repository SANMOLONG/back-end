package com.innocamp.sanmolong.post.dto;

import com.innocamp.sanmolong.post.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TotalPostResponseDto {
    private List<PostResponseDto> uncompleted = new ArrayList<>();
    private List<PostResponseDto> completed = new ArrayList<>();

    public TotalPostResponseDto(List<Post> posts) {
        posts.forEach(post -> {
            PostResponseDto responseDto = PostResponseDto
                    .builder()
                    .postId(post.getId())
                    .title(post.getTitle())
                    .mountain(post.getMountain().getMountain())
                    .course(post.getMountain().getCourse())
                    .level(post.getMountain().getCourseLevel())
                    .mountDate(post.getMountDate())
                    .togetherCount(post.getTogethers().size())
                    .headCount(post.getHeadCount())
                    .build();
            if (post.getCompleted()) {
                completed.add(responseDto);
            } else {
                uncompleted.add(responseDto);
            }
        });
    }
}
