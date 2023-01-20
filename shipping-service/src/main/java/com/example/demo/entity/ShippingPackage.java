package com.example.demo.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class ShippingPackage {

    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address1", column = @Column(name = "origin_address1")),
            @AttributeOverride(name = "address2", column = @Column(name = "origin_address2")),
            @AttributeOverride(name = "city", column = @Column(name = "origin_city")),
            @AttributeOverride(name = "state", column = @Column(name = "origin_state")),
            @AttributeOverride(name = "zip", column = @Column(name = "origin_zip"))
    })
    private Address origin;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address1", column = @Column(name = "dest_address1")),
            @AttributeOverride(name = "address2", column = @Column(name = "dest_address2")),
            @AttributeOverride(name = "city", column = @Column(name = "dest_city")),
            @AttributeOverride(name = "state", column = @Column(name = "dest_state")),
            @AttributeOverride(name = "zip", column = @Column(name = "dest_zip"))
    })
    private Address destination;

    private LocalDateTime shippingTime;

}
