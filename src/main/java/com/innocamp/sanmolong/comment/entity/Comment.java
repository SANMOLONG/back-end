package com.innocamp.sanmolong.comment.entity;

import com.innocamp.sanmolong.global.entity.Timestamped;
import com.innocamp.sanmolong.post.entity.Post;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
}
