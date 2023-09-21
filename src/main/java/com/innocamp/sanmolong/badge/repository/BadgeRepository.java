package com.innocamp.sanmolong.badge.repository;

import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BadgeRepository extends JpaRepository<Badge, Long> {
    Badge findByUserAndMountain(User user, Mountain mountain);
}
