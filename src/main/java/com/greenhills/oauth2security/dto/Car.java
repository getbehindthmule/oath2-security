package com.greenhills.oauth2security.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Car {
    private Long id ;
    private String registrationNumber;
    private Company company;
}