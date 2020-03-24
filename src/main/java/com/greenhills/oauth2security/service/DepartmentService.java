package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.LightweightDepartment;

import java.util.List;
import java.util.Optional;

public interface DepartmentService {
    Department get(Long id);

    LightweightDepartment getLightweight(Long id);

    @SuppressWarnings("unused")
    List<Department> getAll();

    Optional<Long> create(LightweightDepartment department);

   Department update(LightweightDepartment department);
}
