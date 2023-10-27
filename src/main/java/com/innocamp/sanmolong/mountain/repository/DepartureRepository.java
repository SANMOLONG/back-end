package com.innocamp.sanmolong.mountain.repository;

import com.innocamp.sanmolong.mountain.entity.Course;
import com.innocamp.sanmolong.mountain.entity.Departure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartureRepository extends JpaRepository<Departure, Long> {
    Departure findByDepartNmAndCourse(String departNm, Course course);
    Departure findByDepartNm(String departNm);
    List<Departure> findAllByCourse(Course course);
}
