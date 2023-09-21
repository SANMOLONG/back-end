package com.innocamp.sanmolong.mountain.controller;

import com.innocamp.sanmolong.mountain.dto.CourseResponseDto;
import com.innocamp.sanmolong.mountain.dto.MountainResponseDto;
import com.innocamp.sanmolong.mountain.service.MountainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MountainController {
    private final MountainService mountainService;

    @GetMapping("/mountainInfo")
    public List<MountainResponseDto> getMountInfo(@RequestParam String mountain) {
        return mountainService.getMountInfo(mountain);
    }

    @GetMapping("/courseInfo")
    public CourseResponseDto getCourseInfo(@RequestParam String course) {
        return mountainService.getCourseInfo(course);
    }

}
