package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private Long id ;
    private String street;
    private String houseNumber;
    private String zipCode;
}

