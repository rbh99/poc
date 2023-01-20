package com.disney.studios.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.disney.studios.dao.entity.Breed;
import com.disney.studios.dao.entity.Breed_;
import com.disney.studios.dao.entity.Dog;
import com.disney.studios.dao.entity.Dog_;
import com.disney.studios.dao.entity.Picture;
import com.disney.studios.dao.entity.Picture_;
import com.disney.studios.dao.entity.Vote;
import com.disney.studios.dao.entity.Vote_;
import com.disney.studios.domain.PictureSummary;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PictureService {
	
	private final EntityManager em;
	
	private static final String BASE_QUERY = "select "
	    	+ " p.id as id, p.uri as uri, d as dog, count(v.mark) as count, coalesce(sum(v.mark),0) as sum "
			+ " from Picture p "
			+ " INNER JOIN FETCH Dog d on p.dog = d "
			+ " INNER JOIN FETCH Breed b on d.breed = b"
			+ " LEFT JOIN Vote v on v.picture = p";
	
	/**
	 * returns all pictures sorted by breed and number of votes
	 * 
	 * 	
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<PictureSummary> getPictures(){
	 		
		//using HQL
		TypedQuery<Tuple> query = em.createQuery(BASE_QUERY
				+ " GROUP BY p.id "
				+ " ORDER BY b.name ASC, count(v.mark) desc",
				
				Tuple.class);
		
		return query.getResultList().stream().map(this::convertToPictureSummary).collect(Collectors.toList());
			
	}

	
	/**
	 * returns the list of picture summary for a specific breed, sorted by the number of votes
	 * 
	 * cannot use as JPA criteria is not supporting left joins or right joins on unrelated
	 * Entities.
	 * 
	 * @param breed
	 * @return
	 */
	@Deprecated
	@Transactional(readOnly = true)
	public List<PictureSummary> getPictures(String breedName){
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Tuple> query = cb.createTupleQuery();
		
		Root<Picture> root = query.from(Picture.class);
				
		//fetch dog and breed as inner joins
		Join<Picture, Dog> dogjoin = root.join(Picture_.dog, JoinType.INNER);
		Join<Dog, Breed> breedJoin = dogjoin.join(Dog_.breed, JoinType.INNER);
		query.where(cb.equal(root.get(Picture_.dog), dogjoin));
		query.where(cb.equal(root.get(Picture_.dog).get(Dog_.breed), breedJoin));
		
		//JPA Criteria does not support this yet. cannot avoid cross-join on unrelated tables
		Root<Vote> rootVote = query.from(Vote.class); // cannot avoid another root, which creates issues
		rootVote.join(Vote_.picture, JoinType.LEFT);
		query.where(cb.equal(rootVote.get(Vote_.picture).get(Picture_.id), root.get(Picture_.id)));

		
		query.multiselect(root.get(Picture_.id).alias("id"),
				root.get(Picture_.uri).alias("uri"),
				root.get(Picture_.dog).alias("dog"),
				cb.count(rootVote.get("mark")).alias("count"), 
				cb.sum(rootVote.get("mark")).alias("sum") );
		
		//Path<String> breedPath = root.get(Picture_.dog).get(Dog_.breed).get(Breed_.name);
		query.where( cb.equal(breedJoin.get(Breed_.name), breedName ) );
		
		query.groupBy(root.get(Picture_.id));
		query.orderBy(cb.desc(cb.count(rootVote.get("mark"))));
			
		return em.createQuery( query ).getResultList().stream().map(this::convertToPictureSummary).collect(Collectors.toList());
	}
	
	
	/**
	 * get details for a specific picture id
	 * 
	 * @param picId
	 * @param sort
	 * @return
	 */
	@Transactional(readOnly = true)
	public PictureSummary getPictureDetail(int picId){
				
		TypedQuery<Tuple> query = em.createQuery(BASE_QUERY
				+ " WHERE p.id = :pictureId "
				+ " GROUP BY p.id,  p.uri, d"
				
			  ,Tuple.class);
				
		return  convertToPictureSummary(query.setParameter("pictureId", picId).getSingleResult());
	}
	
	
	@Transactional(readOnly = true)
	public List<PictureSummary> getPicturesUsingQuery(String breed){
		
		    TypedQuery<Tuple> query = em.createQuery(BASE_QUERY
				+ " WHERE lower(b.name) like :breed "
				+ " GROUP BY p.id,  p.uri, d"
				+ " ORDER BY count(v.mark) desc"
				
				,Tuple.class);
		
		   
		return query.setParameter("breed",  breed.toLowerCase()).getResultList().stream().map(this::convertToPictureSummary).collect(Collectors.toList());
		
	}
	
   private PictureSummary convertToPictureSummary(Tuple picWithVotes) {
		
		return PictureSummary.builder()
				.id((int)picWithVotes.get("id"))
				.uri((String) picWithVotes.get("uri"))
				.dogId((int)((Dog)picWithVotes.get("dog")).getId())
				.dogName((String)((Dog)picWithVotes.get("dog")).getName())
				.breed((String)((Dog)picWithVotes.get("dog")).getBreed().getName())
				.votes((long)picWithVotes.get("count"))
				.total((long)picWithVotes.get("sum"))
				.build();
	}
	
}
