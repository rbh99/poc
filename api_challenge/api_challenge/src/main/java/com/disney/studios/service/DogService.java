package com.disney.studios.service;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.studios.dao.entity.Breed;
import com.disney.studios.dao.entity.Dog;
import com.disney.studios.dao.entity.Picture;
import com.disney.studios.domain.CreateDogRequest;
import com.disney.studios.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class DogService {

	private final EntityManager em;

	/**
	 * creates a dog record
	 * 
	 * @param request
	 * @throws BusinessException if the record cannot be created
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public void addDog(CreateDogRequest request) {
		try {
			Dog d = new Dog();
			d.setName(request.getDogName());

			Breed foundBreed = em.unwrap(Session.class).bySimpleNaturalId(Breed.class).load(request.getBreedName());
			if (foundBreed == null) {
				Breed b = new Breed();
				b.setName(request.getBreedName());
				em.persist(b);
				foundBreed = b;
			}

			log.debug(" merged breed  {}", foundBreed);
			d.setBreed(foundBreed);

			for (String puri : request.getPictureUris()) {
				Picture p = new Picture();
				p.setUri(puri);
				d.addPicture(p);
			}

			log.debug("persiting dog {}", d);
			em.persist(d);
		} catch (DataAccessException e) {
			log.debug("DataAccessException {}", e);

			throw new BusinessException("exception on adding a dog: " + e.getClass().getSimpleName(), e);
		}
	}

}
