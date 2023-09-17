package com.innocamp.sanmolong.post.service;

import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.service.MountainService;
import com.innocamp.sanmolong.post.dto.PostRequestDto;
import com.innocamp.sanmolong.post.dto.PostResponseDto;
import com.innocamp.sanmolong.post.dto.TotalPostResponseDto;
import com.innocamp.sanmolong.post.entity.Post;
import com.innocamp.sanmolong.post.repository.PostRepository;
import com.innocamp.sanmolong.user.entity.User;
import com.innocamp.sanmolong.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public PostResponseDto getPost(Long id) {
        return new PostResponseDto(findPost(id));
    }

    @Transactional
    public ResponseEntity<PostResponseDto> updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);

        if (!post.getUser().getNickname().equals(requestDto.getNickname())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        post.update(requestDto);

        return ResponseEntity.status(HttpStatus.OK).body(new PostResponseDto(post));
    }

    public ResponseEntity<?> deletePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);

        if (!post.getUser().getNickname().equals(requestDto.getNickname())) {
            throw new IllegalArgumentException("작성자만 삭제할 수 있습니다.");
        }
        postRepository.delete(post);

        return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 성공");
    }

    @Transactional
    public ResponseEntity<PostResponseDto> completePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);

        if (!post.getUser().getNickname().equals(requestDto.getNickname())) {
            throw new IllegalArgumentException("작성자만 완료할 수 있습니다.");
        }
        post.complete();

        return ResponseEntity.status(HttpStatus.OK).body(new PostResponseDto(post));
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }
}
