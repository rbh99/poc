package com.example.demo.repository;

import com.example.demo.entity.Address;
import com.example.demo.entity.ShippingPackage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;


//@DataJpaTest
@SpringBootTest
class PackageRepositoryTest {

    @Autowired
    PackageRepository repository;

    @Test
    void testCreate(){

        ShippingPackage p =  new ShippingPackage();
        p.setShippingTime(LocalDateTime.now());
        p.setOrigin(new Address("address1", "adddress2", "city", "AZ", "85210"));
        p.setDestination(new Address("d_address1", "d_adddress2", "city2", "AZ", "85210"));

        ShippingPackage saved = repository.save(p);

        assertNotNull(saved);
        assertEquals(saved.getShippingTime(), p.getShippingTime());
        assertEquals(saved.getOrigin(), p.getOrigin());

    }

}