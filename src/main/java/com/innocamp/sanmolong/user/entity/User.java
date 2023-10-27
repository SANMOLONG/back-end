package com.innocamp.sanmolong.user.entity;

import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
import com.innocamp.sanmolong.post.entity.Post;
import com.innocamp.sanmolong.together.entity.Together;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    @Builder.Default
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
