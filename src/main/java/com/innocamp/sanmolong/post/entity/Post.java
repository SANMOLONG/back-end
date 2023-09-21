package com.innocamp.sanmolong.post.entity;

import com.innocamp.sanmolong.comment.entity.Comment;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.post.dto.PostRequestDto;
import com.innocamp.sanmolong.together.entity.Together;
import com.innocamp.sanmolong.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int headCount;

    @Column(nullable = false)
    private Boolean completed;

    @Column(nullable = false)
    private LocalDate mountDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    @OneToMany(mappedBy = "post")
    @OrderBy("createdAt desc")
    private List<Comment> comments;

    @OneToMany(mappedBy = "post")
    private List<Together> togethers;

    public Post(PostRequestDto requestDto, User user, Mountain mountain) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.completed = false;
        this.mountDate = requestDto.getMountDate();
        this.headCount = requestDto.getHeadCount();
        this.user = user;
        this.mountain = mountain;
        this.comments = new ArrayList<>();
        this.togethers = new ArrayList<>();
    }

    public void update(PostRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.mountDate = requestDto.getMountDate();
    }

    public void complete() {
        this.completed = !this.completed;
    }
}
