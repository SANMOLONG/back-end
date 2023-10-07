package com.innocamp.sanmolong.post.service;

import com.innocamp.sanmolong.mountain.entity.Course;
import com.innocamp.sanmolong.mountain.entity.Departure;
import com.innocamp.sanmolong.mountain.entity.Mountain;
import com.innocamp.sanmolong.mountain.repository.CourseRepository;
import com.innocamp.sanmolong.mountain.repository.DepartureRepository;
import com.innocamp.sanmolong.mountain.repository.MountainRepository;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final DepartureRepository departureRepository;
    private final MountainRepository mountainRepository;
    private final MountainService mountainService;

    public ResponseEntity<?> createPost(PostRequestDto requestDto) {
        User user = userService.findUser(requestDto.getNickname());
        Course course = courseRepository.findByCourseNm(requestDto.getCourseNm());
        Departure departure = departureRepository.findByDepartNmAndCourse(requestDto.getDepartNm(), course);
        Post post = new Post(requestDto, user, course, departure);
        postRepository.save(post);

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 생성 성공");
    }

    public TotalPostResponseDto getPosts(String courseNm) {
        Course course = courseRepository.findByCourseNm(courseNm);
        List<Departure> departures = departureRepository.findAllByCourse(course);
        List<Post> posts = new ArrayList<>();
        for(Departure departure : departures){
            for(Post post : postRepository.findAllByDeparture(departure)){
                posts.add(post);
            }
        }

        return new TotalPostResponseDto(posts);
    }

    public PostResponseDto getPost(Long id) {
        Post post = findPost(id);
        return new PostResponseDto(post);
    }

    @Transactional
    public ResponseEntity<PostResponseDto> updatePost(Long id, PostRequestDto requestDto) {
        Post post = findPost(id);

        if (!post.getUser().getNickname().equals(requestDto.getNickname())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }

        post.update(requestDto);

        PostResponseDto responseDto = PostResponseDto
                .builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .mountDate(post.getMountDate())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
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

        PostResponseDto responseDto = PostResponseDto
                .builder()
                .postId(post.getId())
                .completed(post.getCompleted())
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    public Post findPost(Long id) {
        return postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 게시글은 존재하지 않습니다."));
    }
}
