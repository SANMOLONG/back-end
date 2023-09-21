package com.innocamp.sanmolong.mountain.repository;

import com.innocamp.sanmolong.mountain.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
    Optional<Mountain> findByMountainAndCourse(String mountain, String course);

    List<Mountain> findAllByMountain(String mount);

    Optional<Mountain> findByMountainAndCourseAndDepartNm(String mountain, String course, String departNm);
}
