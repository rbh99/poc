package com.disney.studios.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.studios.dao.entity.Client;
import com.disney.studios.dao.entity.Picture;
import com.disney.studios.dao.entity.Vote;
import com.disney.studios.domain.VoteRequest;
import com.disney.studios.exception.BusinessException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService {

	private final EntityManager em;

	/**
	 * adds a new client
	 * 
	 * @param username
	 * @throws BusinessException if the record cannot be created
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public void addClient(String username) {

		try {
			Client c = new Client();
			c.setUsername(username);

			em.persist(c);

		} catch (DataAccessException e) {
			log.debug("DataAccessException {}", e);

			throw new BusinessException("exception on adding a client: " + e.getClass().getSimpleName(), e);
		}

	}

	/**
	 * adds a new vote to the database
	 * 
	 * @param voteRequest
	 * @throws BusinessException if the record cannot be created
	 */
	@Transactional(rollbackFor = BusinessException.class)
	public void vote(VoteRequest voteRequest) {
		try {
			TypedQuery<Vote> existingVotes = em
					.createQuery("from Vote v where v.client.id = :clientId and v.picture.id = :pictId", Vote.class);

			existingVotes.setParameter("clientId", voteRequest.getClientId()).setParameter("pictId",
					voteRequest.getPictureId());
			List<Vote> votes = existingVotes.getResultList(); // TODO single result and exception

			if (votes.isEmpty()) {

				Client client = em.find(Client.class, voteRequest.getClientId());
				if (client == null) {
					throw new BusinessException("Can't find Client for ID " + voteRequest.getClientId());
				}
				Picture picture = em.find(Picture.class, voteRequest.getPictureId());
				if (picture == null) {
					throw new BusinessException("Can't find Picture for ID " + voteRequest.getPictureId());
				}

				Vote v = new Vote();
				v.setClient(client);
				v.setPicture(picture);
				v.setMark(voteRequest.getMark());
				em.persist(v);
			} else {
				// do update
				Vote v = votes.get(0);
				if (v.getMark() == voteRequest.getMark()) {
					throw new BusinessException("same mark for same picture is not allowed more than once");
				}
				v.setMark(voteRequest.getMark());
				em.merge(v);
				em.flush();
			}
		} catch (DataAccessException e) {
			log.debug("DataAccessException {}", e);

			throw new BusinessException("exception: " + e.getClass().getSimpleName(), e);
		}

	}

}
