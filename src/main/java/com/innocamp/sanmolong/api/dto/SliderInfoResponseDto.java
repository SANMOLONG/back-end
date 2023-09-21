package com.innocamp.sanmolong.api.dto;

import com.innocamp.sanmolong.badge.dto.SliderBadgeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SliderInfoResponseDto {
    List<WeatherDto> weathers;
    List<FireDto> fires;
    List<SliderBadgeDto> badges;
    long plogging;
}
