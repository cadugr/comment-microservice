package com.anonymous.algacomments.comment.service.domain.repository;

import com.anonymous.algacomments.comment.service.domain.model.Comment;
import com.anonymous.algacomments.comment.service.domain.model.CommentId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, CommentId> {
}
