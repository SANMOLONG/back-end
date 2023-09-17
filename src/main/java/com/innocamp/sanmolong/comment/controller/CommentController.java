package com.innocamp.sanmolong.comment.controller;


import com.innocamp.sanmolong.comment.dto.CommentRequestDto;
import com.innocamp.sanmolong.comment.dto.CommentResponseDto;
import com.innocamp.sanmolong.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    // 댓글 생성
    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.createComment(requestDto);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id,
                                                            @RequestBody CommentRequestDto requestDto) {
        return commentService.updateComment(id, requestDto);
    }
}
