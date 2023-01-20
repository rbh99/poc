package com.disney.studios.dao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;

import lombok.Data;

@Data
@Entity
public class Breed implements Serializable {

	private static final long serialVersionUID = -2490379751834961368L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NaturalId
	@Column(unique = true)
	private String name;
}
