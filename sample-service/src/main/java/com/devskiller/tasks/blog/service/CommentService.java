package com.devskiller.tasks.blog.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.devskiller.tasks.blog.model.Comment;
import com.devskiller.tasks.blog.model.Post;
import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.repository.CommentRepository;
import com.devskiller.tasks.blog.repository.PostRepository;

@Service
public class CommentService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	
	public CommentService(PostRepository postRepository, CommentRepository commentRepository) {
		super();
		this.postRepository = postRepository;
		this.commentRepository = commentRepository;
	}

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		
		return commentRepository.findByPostId(postId)
		.stream()
		.map(c->new CommentDto(c.getId(),c.getContent(), c.getAuthor(), c.getCreationDate()))
		.collect(Collectors.toList());
			

	}

	/**
	 * Creates a new comment
	 *
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 *
	 * @throws IllegalArgumentException if there is no blog post for passed newCommentDto.postId
	 */
	@Transactional
	public Long addComment(NewCommentDto newCommentDto) {
		
		Post post = postRepository.findById(newCommentDto.getPostId()).orElseThrow(()->new IllegalArgumentException("invalid post id"));
		
		Comment c = new Comment();
		c.setPost(post);
		c.setAuthor(newCommentDto.getAuthor());
		c.setContent(newCommentDto.getContent());
		
		Comment saved = commentRepository.saveAndFlush(c);
		
		return saved.getId();
	}
}
