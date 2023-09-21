package com.innocamp.sanmolong.api.controller;

import com.innocamp.sanmolong.api.dto.FireDto;
import com.innocamp.sanmolong.api.dto.SliderInfoResponseDto;
import com.innocamp.sanmolong.api.dto.WeatherDto;
import com.innocamp.sanmolong.api.explorer.FireApiExplorer;
import com.innocamp.sanmolong.api.explorer.WeatherApiExplorer;
import com.innocamp.sanmolong.badge.dto.SliderBadgeDto;
import com.innocamp.sanmolong.badge.service.BadgeService;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/sliderInfo")
@RequiredArgsConstructor
public class SliderInfoController {
    private final FireApiExplorer fireApiExplorer;
    private final WeatherApiExplorer weatherApiExplorer;
    private final UserService userService;
    private final BadgeService badgeService;

    @GetMapping
    public SliderInfoResponseDto getSliderInfo(@RequestParam(required = false) String nickname) throws IOException, ParserConfigurationException, SAXException {

        List<FireDto> fireInfo = fireApiExplorer.getFireInfo();
        List<WeatherDto> weatherInfo = weatherApiExplorer.getWeatherInfo();
        long plogging = 0;
        List<SliderBadgeDto> badges = null;
        if (nickname != null) {
            User user = userService.findUser(nickname);
            // 플로깅
            plogging = user.getPoint();
            // 뱃지
            badges = badgeService.getSliderBadgeInfo(nickname);
        }

        return new SliderInfoResponseDto(weatherInfo, fireInfo, badges, plogging);
    }
}
