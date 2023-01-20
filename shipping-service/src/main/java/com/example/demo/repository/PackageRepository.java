package com.example.demo.repository;

import com.example.demo.entity.ShippingPackage;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PackageRepository extends JpaRepository<ShippingPackage, Long> {

    //<T> T findById(Long id, Class<T>clazz);
}
