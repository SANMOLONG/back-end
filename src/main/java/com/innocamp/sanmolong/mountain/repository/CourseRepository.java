package com.innocamp.sanmolong.mountain.repository;

import com.innocamp.sanmolong.mountain.entity.Course;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findAllByMountain(Mountain mountain);
    Course findByCourseNm(String courseNm);

}
