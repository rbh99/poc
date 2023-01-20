package com.example.demo.controller;

import com.example.demo.service.PackageService;
import com.example.demo.dto.NewPackageDto;
import com.example.demo.dto.PackageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController(value = "")
@RequiredArgsConstructor
@Validated
public class PackageController {

    private final PackageService packageService;

    @GetMapping(value="/package/{id}")
    //@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<PackageDto> getPackage(@PathVariable(name="id")Long id){
       PackageDto dto =  packageService.getPackage(id);
       if (dto == null){
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }else{
           return new ResponseEntity<>(dto, HttpStatus.OK);
       }
    }

    @PutMapping(value = "value=/package/new", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void createPackage(@RequestBody @Valid NewPackageDto packageDto){
        packageService.createNewPackage(packageDto);
    }

    @PostMapping (value="/package", consumes = "application/json", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    void updatePackage(@RequestBody PackageDto pack){
        packageService.updatePackage(pack);
    }

}
