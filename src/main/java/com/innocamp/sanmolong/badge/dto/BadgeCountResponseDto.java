package com.innocamp.sanmolong.badge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BadgeCountResponseDto {
    private String mountainNm;
    private int count;
    private String latelyDate;
}
