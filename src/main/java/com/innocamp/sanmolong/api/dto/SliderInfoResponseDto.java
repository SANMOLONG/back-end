package com.innocamp.sanmolong.api.dto;

import com.innocamp.sanmolong.badge.entity.Badge;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SliderInfoResponseDto {
    List<WeatherDto> weathers;
    List<FireDto> fires;
    List<Badge> badges;
    long plogging;
}
