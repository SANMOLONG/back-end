package com.innocamp.sanmolong.badge.entity;

import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Badge{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private boolean checkBadge;

    @Column
    private LocalDateTime getDate;

    @Column
    @Builder.Default
    private Long badgePoint = 0L;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "mountain_id")
    private Mountain mountain;

    public void update(boolean checkBadge){
        this.checkBadge = checkBadge;
    }
}
