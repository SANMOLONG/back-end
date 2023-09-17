package com.innocamp.sanmolong.post.controller;

import com.innocamp.sanmolong.post.dto.PostRequestDto;
import com.innocamp.sanmolong.post.dto.PostResponseDto;
import com.innocamp.sanmolong.post.dto.TotalPostResponseDto;
import com.innocamp.sanmolong.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto) {
        return postService.createPost(requestDto);
    }

    // 전체 게시글 조회
    @GetMapping
    public TotalPostResponseDto getPosts(@RequestParam String mount, @RequestParam String course) {
        return postService.getPosts(mount, course);
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public PostResponseDto getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    // 선택한 게시글 수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @RequestBody PostRequestDto requestDto) {
        return postService.updatePost(id, requestDto);
    }

    // 선택한 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id,
                                        @RequestBody PostRequestDto requestDto) {
        return postService.deletePost(id, requestDto);
    }

    // 게시글 완료하기
    @PutMapping("/{id}/completed")
    public ResponseEntity<PostResponseDto> completePost(@PathVariable Long id,
                                          @RequestBody PostRequestDto requestDto) {
        return postService.completePost(id, requestDto);
    }
}