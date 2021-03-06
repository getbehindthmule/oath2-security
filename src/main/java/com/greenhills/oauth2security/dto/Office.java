package com.greenhills.oauth2security.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@Builder
@ToString(exclude = {"department"})
@EqualsAndHashCode(exclude = {"department"})
public class Office {
    private Long id ;
    private String name;
    private Address address;
    @JsonBackReference
    private Department department;
}
