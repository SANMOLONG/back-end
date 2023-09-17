package com.innocamp.sanmolong.user.entity;

import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.post.entity.Post;
import com.innocamp.sanmolong.together.entity.Together;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private Long point = 0L;

    @Column
    private Long age;

    @Column
    private String sex;

    @OneToMany(mappedBy = "user")
    private List<Badge> badges = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Together> togethers = new ArrayList<>();
}
