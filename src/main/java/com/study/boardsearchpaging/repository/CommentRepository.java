package com.study.boardsearchpaging.repository;

import com.study.boardsearchpaging.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
