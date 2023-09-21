package com.innocamp.sanmolong.user;

import com.innocamp.sanmolong.badge.entity.Badge;
import com.innocamp.sanmolong.badge.repository.BadgeRepository;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MountainRepository mountainRepository;

    @Autowired
    private BadgeRepository badgeRepository;

    @Test
    void createUser() {
        User user = User.builder()
                .id(1L)
                .nickname("모롱이")
                .age(10L)
                .sex("남")
                .build();
        userRepository.save(user);
    }

    @Test
    void createBadge(){
        Optional<User> user = userRepository.findByNickname("모롱이");
        List<Mountain> mountains = mountainRepository.findAll();
        for(int i = 0; i < mountains.size(); i++){
            Badge badge = Badge.builder()
                    .getDate(LocalDateTime.now())
                    .checkBadge(false)
                    .user(user.get())
                    .mountain(mountains.get(i))
                    .build();

            badgeRepository.save(badge);
        }
    }

}
