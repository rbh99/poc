package com.example.demo.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PackageDto {

    private final Long id;

    private final Address origin;
    private final Address destination;
    private final LocalDateTime shippingDate;

}
