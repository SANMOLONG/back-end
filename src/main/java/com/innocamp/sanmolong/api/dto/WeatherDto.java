package com.innocamp.sanmolong.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WeatherDto {
    String mount;
    boolean hiking;
    String sunrise;
    String sunset;
    int forecast;
}
