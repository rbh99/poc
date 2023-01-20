package com.example.carservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping (value="/cars", produces = "application/json")
@Slf4j
public class CarController {
	
	
	@GetMapping("/all")
    public String getAllCars() { 
		
		log.debug("all cars requested");
        return "cars to be populated";
    }
	

}
