package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Office {
    private Long id ;
    private String name;
    private Address address;
    private Department department;
}
