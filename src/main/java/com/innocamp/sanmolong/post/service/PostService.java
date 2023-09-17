package com.innocamp.sanmolong.post.service;

import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.service.MountainService;
import com.innocamp.sanmolong.post.dto.PostRequestDto;
import com.innocamp.sanmolong.post.dto.TotalPostResponseDto;
import com.innocamp.sanmolong.post.entity.Post;
import com.innocamp.sanmolong.post.repository.PostRepository;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserService userService;
    private final MountainService mountainService;

    public ResponseEntity<?> createPost(PostRequestDto requestDto) {
        User user = userService.findUser(requestDto.getNickname());
        Mountain mountain = mountainService.findMountain(requestDto.getMountain(), requestDto.getCourse());
        Post post = new Post(requestDto, user, mountain);
        postRepository.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 생성 성공");
    }

    public TotalPostResponseDto getPosts(String mount, String course) {
        List<Post> posts = postRepository.findByMountain_MountainAndMountain_Course(mount, course);
        return new TotalPostResponseDto(posts);
    }

}
