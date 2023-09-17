package com.innocamp.sanmolong.post.controller;

import com.innocamp.sanmolong.post.dto.PostRequestDto;
import com.innocamp.sanmolong.post.dto.PostResponseDto;
import com.innocamp.sanmolong.post.dto.TotalPostResponseDto;
import com.innocamp.sanmolong.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping("/posts")
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 전체 게시글 조회
    @GetMapping("/posts")
    public TotalPostResponseDto getPosts(@RequestParam String mount, @RequestParam String course) {
        return postService.getPosts(mount, course);
    }

    // 게시글 상세 조회
    @GetMapping("/posts/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }
}