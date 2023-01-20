package com.devskiller.tasks.blog.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@NamedQuery(name = "Comments_findByPostId", query ="from Comment c where c.post.id = :postId")
public class Comment {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String author;

	@Column(length = 4096)
	private String content;

	@ManyToOne()
	@JoinColumn(name = "post_id", referencedColumnName = "id")
	private Post post;

	@CreationTimestamp
	private LocalDateTime creationDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	@Override
	public String toString() {
		return "Comment [id=" + id + ", author=" + author + ", content=" + content + ", post=" + post
				+ ", creationDate=" + creationDate + "]";
	}
	
	

}
