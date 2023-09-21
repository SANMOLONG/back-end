package com.innocamp.sanmolong.badge.repository;

import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Optional<Badge> findFirstByUser_NicknameAndMountain_MountainAndCheckBadgeTrueOrderByGetDateDesc(String nickname, String mountain);
    long countByUser_NicknameAndMountain_MountainAndCheckBadgeTrue(String nickname, String mountain);
    Badge findByUserAndMountain(User user, Mountain mountain);
}
