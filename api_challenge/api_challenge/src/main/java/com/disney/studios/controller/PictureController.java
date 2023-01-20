package com.disney.studios.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.disney.studios.domain.PictureSummary;
import com.disney.studios.service.PictureService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/pictures", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@RequiredArgsConstructor
public class PictureController {

	private final PictureService pictureService;

	/**
	 * List all of the available dog pictures grouped by breed
	 * the results are sorted by breed and number of votes
	 * 
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/all")
	public List<PictureSummary> getAllPictures() {
		// TODO proper sort argument
		return pictureService.getPictures();
	}

	/**
	 * List all of the available dog pictures of a particular breed.
	 * the results will be sorted by the number of votes
	 * 
	 * @param name
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/breed/{name}")
	public List<PictureSummary> getPictures(@PathVariable("name") @Valid @NotBlank String name) {
		
		return pictureService.getPicturesUsingQuery(name);
		//return pictureService.getPictures(name);
	}

	/**
	 * returns details associated with a single picture
	 * @param id
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/id/{id}")
	public PictureSummary getPictureDetail(@PathVariable("id") int id) {
		
		return pictureService.getPictureDetail(id);
	}
}
