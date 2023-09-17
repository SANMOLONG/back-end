package com.innocamp.sanmolong.mountain.service;

import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MountainService {
    private final MountainRepository mountainRepository;

    public Mountain findMountain(String mountain, String course) {
        return mountainRepository.findByMountainAndCourse(mountain, course)
                .orElseThrow(() -> new IllegalArgumentException("해당 산에 대한 코스가 존재하지 않습니다."));
    }
}
