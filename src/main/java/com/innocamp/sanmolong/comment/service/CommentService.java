package com.innocamp.sanmolong.comment.service;

import com.innocamp.sanmolong.comment.dto.CommentRequestDto;
import com.innocamp.sanmolong.comment.dto.CommentResponseDto;
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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public ResponseEntity<CommentResponseDto> updateComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findComment(id);

        if(!comment.getUser().getNickname().equals(requestDto.getNickname())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        comment.update(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new CommentResponseDto(comment));
    }

    public ResponseEntity<?> deleteComment(Long id, CommentRequestDto requestDto) {
        Comment comment = findComment(id);

        if(!comment.getUser().getNickname().equals(requestDto.getNickname())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);

        return ResponseEntity.status(HttpStatus.OK).body("댓글 삭제 성공");
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 댓글은 존재하지 않습니다."));
    }
}
