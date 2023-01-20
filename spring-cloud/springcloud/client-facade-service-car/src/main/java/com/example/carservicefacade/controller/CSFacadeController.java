package com.example.carservicefacade.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/filtered", produces = "application/json")
@Slf4j
@RequiredArgsConstructor
public class CSFacadeController {
	
	private final CarServiceClient carClient;
	
	@GetMapping("/all")
    public String getAllFilteredCars() { 
		
		log.debug("all filtered cars requested");
        return "filtered " + carClient.readCars();
    }

}
