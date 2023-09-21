package com.innocamp.sanmolong.mountain.service;

import com.innocamp.sanmolong.mountain.dto.CourseResponseDto;
import com.innocamp.sanmolong.mountain.dto.MountainResponseDto;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MountainService {
    private final MountainRepository mountainRepository;

    public List<MountainResponseDto> getMountInfo(String mountain) {
        List<Mountain> mountains = mountainRepository.findAllByMountain(mountain);
        List<MountainResponseDto> responseDtoList = new ArrayList<>();
        mountains.forEach(m -> {
            MountainResponseDto responseDto = new MountainResponseDto(
                    m.getCoursePic(),
                    m.getCourse(),
                    m.getCourseLevel()
            );
            responseDtoList.add(responseDto);
        });
        return responseDtoList;
    }

    public CourseResponseDto getCourseInfo(String course) {
        Mountain c = mountainRepository.findByCourse(course);

        CourseResponseDto responseDto = new CourseResponseDto(
                c.getMountain(),
                c.getCourse(),
                c.getCourseLevel(),
                c.getSpendTime(),
                c.getCourseImg(),
                c.getCoursePhone(),
                c.getDepartNm(),
                c.getDepartAd(),
                c.getCourseDetail(),
                c.getCoursePic()
        );

        return responseDto;
    }

    public Mountain findMountain(String mountain, String course) {
        return mountainRepository.findByMountainAndCourse(mountain, course)
                .orElseThrow(() -> new IllegalArgumentException("해당 산에 대한 코스가 존재하지 않습니다."));
    }

}
