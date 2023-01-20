package com.disney.studios.dao.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString(of = {"id", "name"})
public class Dog implements Serializable{

	private static final long serialVersionUID = -5895594071886790890L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER) //EAGER is the default
	@JoinColumn(name="breed_id", nullable=false, referencedColumnName = "id")
	private Breed breed;
	
	@OneToMany(mappedBy="dog", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Picture> pictures = new ArrayList<>();
	
	public void addPicture(Picture p) {
		pictures.add(p);
		p.setDog(this);
	}

	public void removePicture(Picture p) {
		pictures.remove(p);
		p.setDog(null);
	}
}
