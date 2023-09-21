package com.innocamp.sanmolong.mountain.repository;

import com.innocamp.sanmolong.mountain.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MountainRepository extends JpaRepository<Mountain, Long> {
    Optional<Mountain> findByMountainAndCourse(String mountain, String course);

    Optional<Mountain> findByMountainAndCourseAndDepartNm(String mountain, String course, String departNm);

    @Query("select m from Mountain m where m.mountain = ?1 group by m.course")
    List<Mountain> findAllByMountain(String mountain);

    @Query("select m from Mountain m where m.course = ?1 group by m.course")
    Mountain findByCourse(String course);
}
