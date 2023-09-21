package com.innocamp.sanmolong.badge.controller;

import com.innocamp.sanmolong.badge.dto.BadgeCountResponseDto;
import com.innocamp.sanmolong.badge.dto.BadgeMountainRequestDto;
import com.innocamp.sanmolong.badge.dto.BadgeRequestDto;
import com.innocamp.sanmolong.badge.dto.BadgeResponseDto;
import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.badge.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;

    // 뱃지 획득
    @PostMapping
    public boolean createBadge(@RequestBody BadgeRequestDto requestDto){
        return badgeService.createBadge(requestDto);
    }

    // 사용자별 획득 뱃지 조회
    @GetMapping("/mountDetail")
    public List<BadgeResponseDto> getBadges(@RequestBody BadgeMountainRequestDto requestDto){
        return badgeService.getBadges(requestDto);
    }

    //오늘 날짜 사용자별 뱃지 조회
    @GetMapping("/today")
    public List<BadgeResponseDto> getBadgesToday(@RequestBody BadgeMountainRequestDto requestDto){
        return badgeService.getBadgesToday(requestDto);
    }

    @GetMapping("/count/{nickname}")
    public List<BadgeCountResponseDto> getBadgesCount(@PathVariable("nickname") String nickname){
        return badgeService.getBadgesCount(nickname);
    }
}
