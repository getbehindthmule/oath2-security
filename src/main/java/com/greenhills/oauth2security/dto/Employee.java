package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private Long id ;
    private String name;
    private String surname;
    private Address address;
    private Department department;
}
