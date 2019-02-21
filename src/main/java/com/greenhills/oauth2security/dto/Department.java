package com.greenhills.oauth2security.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;

@Data
@Builder
@ToString(exclude = "company")
@EqualsAndHashCode(exclude = "company")
public class Department {
    private Long id ;
    private String name;
    @JsonManagedReference
    private Set<Employee> employees ;
    @JsonManagedReference
    private Set<Office> offices ;
    @JsonBackReference
    private Company company;
}
