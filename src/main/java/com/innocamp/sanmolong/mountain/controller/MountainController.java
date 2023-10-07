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
    public List<MountainResponseDto> getMountInfo(@RequestParam String mountainNm) {
        return mountainService.getMountInfo(mountainNm);
    }

    @GetMapping("/courseInfo")
    public CourseResponseDto getCourseInfo(@RequestParam String courseNm, @RequestParam String departNm) {
        return mountainService.getCourseInfo(courseNm, departNm);
    }

    @GetMapping("/courseInfoForPost")
    public CourseResponseDto getCourseInfoForPost(@RequestParam String courseNm, @RequestParam String departNm) {
        return mountainService.getCourseInfoForPost(courseNm, departNm);
    }
}
