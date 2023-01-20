package com.disney.studios.dao.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import lombok.Data;

@Data
@Entity
public class Vote implements Serializable{

	private static final long serialVersionUID = 7746120980942016879L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "client_id", referencedColumnName = "id", insertable = false, updatable = false)
	@Id
	private Client client;

	@ManyToOne
	@JoinColumn(name = "picture_id", referencedColumnName = "id", insertable = false, updatable = false)
	@Id
	private Picture picture;

	
	private int mark;
	
	@Valid
	private boolean isMarkValid(){
		return mark ==1 || mark == -1;
	}

}
