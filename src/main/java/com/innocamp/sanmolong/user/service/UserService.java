package com.innocamp.sanmolong.user.service;

import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    public User findUser(String nickname){
        return userRepository.findByNickname(nickname).orElseThrow(() ->
                new IllegalArgumentException("등록된 사용자가 없습니다."));
    }
}
