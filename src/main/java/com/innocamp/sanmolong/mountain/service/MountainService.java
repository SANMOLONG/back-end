package com.innocamp.sanmolong.mountain.service;

import com.innocamp.sanmolong.mountain.dto.CourseResponseDto;
import com.innocamp.sanmolong.mountain.dto.MountainResponseDto;
import com.innocamp.sanmolong.mountain.entity.Course;
import com.innocamp.sanmolong.mountain.entity.Departure;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.CourseRepository;
import com.innocamp.sanmolong.mountain.repository.DepartureRepository;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {
    private final CourseRepository courseRepository;
    private final MountainRepository mountainRepository;
    private final DepartureRepository departureRepository;

    public List<MountainResponseDto> getMountInfo(String mountainNm) {
        Mountain mountain = mountainRepository.findByMountainNm(mountainNm);
        List<Course> courses = courseRepository.findAllByMountain(mountain);
        List<MountainResponseDto> responseDtoList = new ArrayList<>();
        courses.forEach(m -> {
            MountainResponseDto responseDto = new MountainResponseDto(
                    m.getCoursePic(),
                    m.getCourseNm(),
                    m.getCourseLevel()
            );
            responseDtoList.add(responseDto);
        });
        return responseDtoList;
    }

    public CourseResponseDto getCourseInfo(String courseNm, String departNm) {
        Course course = courseRepository.findByCourseNm(courseNm);
        Departure departure = departureRepository.findByDepartNm(departNm);

        CourseResponseDto responseDto = new CourseResponseDto(
                course.getMountain().getMountainNm(),
                course.getCourseNm(),
                course.getCourseLevel(),
                course.getSpendTime(),
                course.getCourseImg(),
                course.getCoursePhone(),
                departure.getDepartNm(),
                departure.getDepartAd(),
                course.getCourseDetail(),
                course.getCoursePic()
        );

        return responseDto;
    }

    public CourseResponseDto getCourseInfoForPost(String courseNm, String departNm) {
        Course course = courseRepository.findByCourseNm(courseNm);
        Departure departure = departureRepository.findByDepartNm(departNm);

        CourseResponseDto responseDto = CourseResponseDto.builder()
                .mountain(course.getMountain().getMountainNm())
                .course(course.getCourseNm())
                .level(course.getCourseLevel())
                .spendTime(course.getSpendTime())
                .departNM(departure.getDepartNm())
                .departAD(departure.getDepartAd())
                .courseDetail(course.getCourseDetail())
                .build();

        return responseDto;
    }

//    public Mountain findMountainStart(String mountain, String course, String departNm) {
//        return mountainRepository.findByMountainAndCourseAndDepartNm(mountain, course, departNm)
//                .orElseThrow(() -> new IllegalArgumentException("해당 산에 대한 출발지가 존재하지 않습니다."));
//    }
//
//    public Mountain findMountain(String mountain, String course) {
//        return mountainRepository.findByMountainAndCourse(mountain, course)
//                .orElseThrow(() -> new IllegalArgumentException("해당 산에 대한 코스가 존재하지 않습니다."));
//    }

}
