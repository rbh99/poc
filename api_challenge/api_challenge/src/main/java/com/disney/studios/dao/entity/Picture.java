package com.disney.studios.dao.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@ToString(of = {"id", "uri"})
public class Picture implements Serializable{

	private static final long serialVersionUID = 5660992944916993643L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String uri;

	@ManyToOne
	private Dog dog;


}
