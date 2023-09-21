package com.innocamp.sanmolong.badge.dto;

import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@NoArgsConstructor
public class BadgeResponseDto {
    private Long id;
    private boolean checkBadge;
    private String getDate;
    private Long badgePoint;
    private String nickname;
    private String mountain;
    private String course;

    public BadgeResponseDto(Badge badge){
        this.id = badge.getId();
        this.getDate = badge.getGetDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.badgePoint = badge.getBadgePoint();
        this.checkBadge = badge.isCheckBadge();
        this.nickname = badge.getUser().getNickname();
        this.mountain = badge.getMountain().getMountain();
        this.course = badge.getMountain().getCourse();
    }
}
