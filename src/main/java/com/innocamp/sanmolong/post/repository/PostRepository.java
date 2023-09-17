package com.innocamp.sanmolong.post.repository;

import com.innocamp.sanmolong.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByMountain_MountainAndMountain_Course(String mountain, String course);

}
