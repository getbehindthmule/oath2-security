package com.greenhills.oauth2security.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class LightweightDepartment {
    private Long id ;
    private String name;
    private Set<Long> employeeIds ;
    private Set<Long> officeIds ;
    private Long companyId;
}
