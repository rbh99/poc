package com.devskiller.tasks.blog.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devskiller.tasks.blog.model.dto.CommentDto;
import com.devskiller.tasks.blog.model.dto.NewCommentDto;
import com.devskiller.tasks.blog.model.dto.PostDto;
import com.devskiller.tasks.blog.service.CommentService;
import com.devskiller.tasks.blog.service.PostService;

@Controller
@RestController
@RequestMapping("/posts")
@Validated
public class PostController {

	private final PostService postService;
	private final CommentService commentService;


	public PostController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PostDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}
	
	/**
	 * returns all comments for a post with passed {id}
	 */
	@GetMapping(value= "{id}/comments")
	@ResponseStatus(HttpStatus.OK)
	public List<CommentDto> getCommentsForPost (@PathVariable Long id) {
		
		return commentService.getCommentsForPost(id);
		
	}

	/**
	 * save a new comment for post with passed {id} or return 404 Not Found if post doesn't exist
	 */
	@PostMapping(value="{id}/comments")
	@ResponseStatus(HttpStatus.CREATED)
	public Long addComment(@PathVariable Long id, @Valid @RequestBody NewCommentDto comment) {
		
		return commentService.addComment(comment);
	}

			
}