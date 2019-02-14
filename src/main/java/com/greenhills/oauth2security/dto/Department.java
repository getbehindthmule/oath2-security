package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class Department {
    private Long id ;
    private String name;
    private Set<Employee> employees ;
    private Set<Office> offices ;
    private Company company;
}
