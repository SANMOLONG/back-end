package com.innocamp.sanmolong.mountain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CourseResponseDto {
    String mountain;
    String course;
    String level;
    String spendTime;
    String courseImg;
    String coursePhone;
    String departNM;
    String departAD;
    String courseDetail;
    String coursePic;
}
