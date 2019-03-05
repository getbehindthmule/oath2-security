package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LightweightCar {
    private Long id ;
    private String registrationNumber;
    private Long companyId;
}
