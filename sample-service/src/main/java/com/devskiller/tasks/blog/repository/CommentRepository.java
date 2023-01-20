package com.devskiller.tasks.blog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.devskiller.tasks.blog.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	@Query(name = "Comments_findByPostId")
	public List<Comment> findByPostId(@Param("postId") Long postId);

}
