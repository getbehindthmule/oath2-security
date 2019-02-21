package com.greenhills.oauth2security.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Company {
    private Long id ;
    private String name;
    @JsonManagedReference
    private Set<Department> departments ;
    @JsonManagedReference
    private Set<Car> cars;
}
