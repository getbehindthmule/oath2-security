package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@ToString(exclude = "company")
@EqualsAndHashCode(exclude = "company")
public class Department {
    private Long id ;
    private String name;
    private Set<Employee> employees ;
    private Set<Office> offices ;
    private Company company;
}
