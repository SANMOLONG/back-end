package com.innocamp.sanmolong.badge.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class BadgeRequestDto {
    private String nickname;
    private String mountainNm;
    private String courseNm;
    private double userLatitude;
    private double userLongitude;
    private String departNm;
    private LocalDate containDate;
}
