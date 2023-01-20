package com.example.demo.service;

import com.example.demo.dto.Address;
import com.example.demo.dto.NewPackageDto;
import com.example.demo.dto.PackageDto;
import com.example.demo.entity.ShippingPackage;
import com.example.demo.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PackageService {

    private final PackageRepository repository;

    public PackageDto getPackage(Long id) {
        return repository.findById(id).map(p->PackageDto.builder().origin(
                Address.builder()
                        .address1(p.getOrigin().getAddress1())
                        .address2(p.getOrigin().getAddress2())
                                .city(p.getOrigin().getCity())
                                        .state(p.getOrigin().getState())
                                                .zip(p.getOrigin().getZip())
                        .build()
                )
                .destination(
                        Address.builder()
                                .address1(p.getDestination().getAddress1())
                                .address2(p.getDestination().getAddress2())
                                .city(p.getDestination().getCity())
                                .state(p.getDestination().getState())
                                .zip(p.getDestination().getZip())
                                .build()
                ).shippingDate(p.getShippingTime()).build()).orElse(null);
    }

    public void createNewPackage(NewPackageDto dto) {
        ShippingPackage p = new ShippingPackage();
        //convert dto tp p
        p.setShippingTime(dto.getShippingDate());
        p.setOrigin(new com.example.demo.entity.Address(
                dto.getOrigin().getAddress1(),
                dto.getOrigin().getAddress2(),
                dto.getOrigin().getCity(),
                dto.getOrigin().getState(),
                String.valueOf(dto.getOrigin().getZip())));
        p.setDestination(new com.example.demo.entity.Address(
                dto.getDestination().getAddress1(),
                dto.getDestination().getAddress2(),
                dto.getDestination().getCity(),
                dto.getDestination().getState(),
                String.valueOf(dto.getDestination().getZip())));

        repository.save(p);
    }

    public void updatePackage(PackageDto dto) {
        ShippingPackage p = new ShippingPackage();
        //convert dto tp p
        p.setId(dto.getId());
        p.setShippingTime(dto.getShippingDate());
        p.setOrigin(new com.example.demo.entity.Address(
                dto.getOrigin().getAddress1(),
                dto.getOrigin().getAddress2(),
                dto.getOrigin().getCity(),
                dto.getOrigin().getState(),
                String.valueOf(dto.getOrigin().getZip())));
        p.setDestination(new com.example.demo.entity.Address(
                dto.getDestination().getAddress1(),
                dto.getDestination().getAddress2(),
                dto.getDestination().getCity(),
                dto.getDestination().getState(),
                String.valueOf(dto.getDestination().getZip())));

        repository.save(p);
    }
}
