package com.innocamp.sanmolong.together.service;

import com.innocamp.sanmolong.post.entity.Post;
import com.innocamp.sanmolong.post.service.PostService;
import com.innocamp.sanmolong.together.dto.TogetherRequestDto;
import com.innocamp.sanmolong.together.entity.Together;
import com.innocamp.sanmolong.together.repostiory.TogetherRepository;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TogetherService {
    private final TogetherRepository togetherRepository;
    private final UserService userService;
    private final PostService postService;

    public ResponseEntity<?> together(TogetherRequestDto requestDto) {
        Optional<Together> optionalTogether = togetherRepository.findByUser_NicknameAndPost_Id(requestDto.getNickname(), requestDto.getPostId());
        if (optionalTogether.isPresent()) {
            togetherRepository.delete(optionalTogether.get());
            return ResponseEntity.status(HttpStatus.OK).body("참여 취소 성공");
        }

        User user =  userService.findUser(requestDto.getNickname());
        Post post = postService.findPost(requestDto.getPostId());
        Together together = new Together(user, post);
        togetherRepository.save(together);

        return ResponseEntity.status(HttpStatus.CREATED).body("참여 성공");
    }
}
