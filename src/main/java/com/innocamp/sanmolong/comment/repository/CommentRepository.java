package com.innocamp.sanmolong.comment.repository;

import com.innocamp.sanmolong.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
