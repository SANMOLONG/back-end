package com.innocamp.sanmolong.api;

import com.innocamp.sanmolong.api.dto.FireDto;
import com.innocamp.sanmolong.api.dto.SliderInfoRequestDto;
import com.innocamp.sanmolong.api.dto.SliderInfoResponseDto;
import com.innocamp.sanmolong.api.dto.WeatherDto;
import com.innocamp.sanmolong.api.explorer.FireApiExplorer;
import com.innocamp.sanmolong.api.explorer.WeatherApiExplorer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sliderInfo")
@RequiredArgsConstructor
public class SliderInfoController {
    private final FireApiExplorer fireApiExplorer;
    private final WeatherApiExplorer weatherApiExplorer;

    @GetMapping
    public SliderInfoResponseDto getSliderInfo(Optional<SliderInfoRequestDto> requestDto) throws IOException, ParserConfigurationException, SAXException {

        List<FireDto> fireInfo = fireApiExplorer.getFireInfo();
        List<WeatherDto> weatherInfo = weatherApiExplorer.getWeatherInfo();
        int plogging = 0;
        if (requestDto.isPresent()) {
            // 플로깅
            // 뱃지
        }

        return new SliderInfoResponseDto(weatherInfo, fireInfo, null, plogging);
    }
}
