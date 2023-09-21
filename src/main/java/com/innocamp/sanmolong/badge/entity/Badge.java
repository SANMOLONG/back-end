package com.innocamp.sanmolong.badge.entity;

import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Badge{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean checkBadge;

    @Column
    private LocalDateTime getDate;

    @Column
    private Long badgePoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;
}
