package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LightweightEmployee {
    private Long id ;
    private String name;
    private String surname;
    private Address address;
    private Long departmentId;
}
