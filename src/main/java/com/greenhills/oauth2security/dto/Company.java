package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Company {
    private Long id ;
    private String name;
    private Set<Department> departments ;
    private Set<Car> cars;
}
