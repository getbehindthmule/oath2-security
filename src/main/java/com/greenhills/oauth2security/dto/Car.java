package com.greenhills.oauth2security.dto;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@ToString(exclude = "company")
@EqualsAndHashCode(exclude = "company")
public class Car {
    private Long id ;
    private String registrationNumber;
    @JsonBackReference
    private Company company;
}
