package com.innocamp.sanmolong.comment.repository;

import com.innocamp.sanmolong.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
