package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LightweightCompany {
    private Long id ;
    private String name;
    private Set<Long> departmentIds ;
    private Set<Long> carIds;
}
