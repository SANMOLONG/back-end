package com.innocamp.sanmolong.comment.service;

import com.innocamp.sanmolong.comment.dto.CommentRequestDto;
import com.innocamp.sanmolong.comment.entity.Comment;
import com.innocamp.sanmolong.comment.repository.CommentRepository;
import com.innocamp.sanmolong.post.entity.Post;
import com.innocamp.sanmolong.post.service.PostService;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final PostService postService;

    public ResponseEntity<?> createComment(CommentRequestDto requestDto){
        Post post = postService.findPost(requestDto.getPostId());
        User user = userService.findUser(requestDto.getNickname());

        Comment comment = new Comment(requestDto, post, user);

        commentRepository.save(comment);

        return ResponseEntity.status(HttpStatus.CREATED).body("댓글 생성 성공");
    }
}
