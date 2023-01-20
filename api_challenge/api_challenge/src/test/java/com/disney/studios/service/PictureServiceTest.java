package com.disney.studios.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.disney.studios.TestConfig;
import com.disney.studios.domain.PictureSummary;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@ContextConfiguration (classes = TestConfig.class)
@DataJpaTest
@Slf4j
//@Sql({"/data.sql"})
public class PictureServiceTest {
	
	@Autowired
	private PictureService pictureService;


	@Test
	public void testLoadedPictures_success() {

		List<PictureSummary> pictures = pictureService.getPictures();
		//assertTrue(pictures.size() > 0);
		assertEquals("picture size", 3, pictures.size());
		
		log.info("testLoadedPictures_success retrieved all pictures {} sorted by breed", pictures);
		
		//check sorted by breed
		List<PictureSummary> sortedPics = new ArrayList<>(pictures);
		sortedPics.sort( (p1,p2) -> p1.getBreed().compareTo(p2.getBreed()));
		assertEquals(sortedPics, pictures);
		
	}

	
	//@Test
	public void testLoadedPicturesByBreed_success() {

		String breedName = "breed1";
		
		List<PictureSummary> pictures = pictureService.getPictures(breedName);
		
		log.info("testLoadedPicturesByBreed_success retrieved all pictures for the breed {}: {}", breedName, pictures);
		
		assertEquals("picture size", 1, pictures.size());
		PictureSummary sum = pictures.get(0);
		
		assertEquals("breed is the same ", breedName, sum.getBreed());
		
		assertEquals("vote count ", 1, sum.getVotes());
		assertEquals("vote total ", 1, sum.getTotal());
		
	}
	
	//@Test
	public void testGetPicturesUsingQuery_success() {

		String breedName = "breed1";
		
		List<PictureSummary> pictures = pictureService.getPicturesUsingQuery(breedName);
		
		log.info("testLoadedPicturesByBreed_success retrieved all pictures for the breed {}: {}", breedName, pictures);

		assertEquals("picture size", 1, pictures.size());
		PictureSummary sum = pictures.get(0);
		
		assertEquals("breed is the same ", breedName, sum.getBreed());
		
		assertEquals("vote count ", 1, sum.getVotes());
		assertEquals("vote total ", 1, sum.getTotal());
		
	}
	
	
	@Test
	public void testGetPicturesDetails_success() {

		int picId = 1;
		
		PictureSummary picture = pictureService.getPictureDetail(picId);
		
		log.info("testGetPicturesDetails_success retrieved detail {}", picture);

		assertEquals("id ", picId, picture.getId());
		
		assertEquals("vote count ", 2, picture.getVotes());
		assertEquals("vote total ", 0, picture.getTotal());

	}
	
	
}
