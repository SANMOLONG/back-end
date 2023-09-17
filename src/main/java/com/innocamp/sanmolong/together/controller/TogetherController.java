package com.innocamp.sanmolong.together.controller;

import com.innocamp.sanmolong.together.dto.TogetherRequestDto;
import com.innocamp.sanmolong.together.service.TogetherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/together")
@RequiredArgsConstructor
public class TogetherController {
    private final TogetherService togetherService;
    @PostMapping
    public ResponseEntity<?> together(@RequestBody TogetherRequestDto requestDto) {
        return togetherService.together(requestDto);
    }
}
