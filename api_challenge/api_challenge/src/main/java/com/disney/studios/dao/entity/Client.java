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
public class Client implements Serializable{

	private static final long serialVersionUID = -4467557127434155133L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NaturalId
	@Column(unique = true)
	private String username;
	
}
