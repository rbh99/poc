package com.example.demo.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Valid
public class NewPackageDto {

    @NotNull
    private final NewAddress origin;
    @NotNull
    private final NewAddress destination;
    @NotNull
    private final LocalDateTime shippingDate;




    @Valid
    @Data
    public static class NewAddress {
        @NotBlank
        private final String address1;

        private final String address2;

        @NotBlank
        private String city;

        @Size(min = 2, max=2)
        private String state;

        @Size(min=5, max=5)
        @Positive
        private Integer zip;
    }
}
