package com.example.carservicefacade.controller;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "car-service")
public interface CarServiceClient {

	
    @GetMapping("/cars/all")
    @CrossOrigin
    String readCars();
	
}
