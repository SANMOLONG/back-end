package com.innocamp.sanmolong.together.repostiory;

import com.innocamp.sanmolong.together.entity.Together;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TogetherRepository extends JpaRepository<Together, Long> {
    Optional<Together> findByUser_NicknameAndPost_Id(String nickname, Long id);
}
