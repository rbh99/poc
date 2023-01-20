package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {

    private final String address1;
    private final String address2;
    private String city;
    private String state;
    private String zip;

}
