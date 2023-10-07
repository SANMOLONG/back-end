package com.innocamp.sanmolong.post.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.innocamp.sanmolong.comment.dto.CommentResponseDto;
import com.innocamp.sanmolong.post.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private LocalDate mountDate;
    private boolean completed;
    private String author;
    private List<CommentResponseDto> comments;
    private Integer togetherCount;
    private Integer headCount;

    private String mountain;
    private String course;
    private String level;
    private String spendTime;
    private String courseImg;
    private String coursePhone;
    private String departNM;
    private String departAD;
    private String courseDetail;
    private String coursePic;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.mountDate = post.getMountDate();
        this.completed = post.getCompleted();
        this.author = post.getUser().getNickname();
        this.comments = post.getComments()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
        this.mountain = post.getDeparture().getCourse().getMountain().getMountainNm();
        this.course = post.getDeparture().getCourse().getCourseNm();
        this.level = post.getDeparture().getCourse().getCourseLevel();
        this.spendTime = post.getDeparture().getCourse().getSpendTime();
        this.departNM = post.getDeparture().getDepartNm();
        this.departAD = post.getDeparture().getDepartAd();
        this.courseDetail = post.getDeparture().getCourse().getCourseDetail();
    }
}
