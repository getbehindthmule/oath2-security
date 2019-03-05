package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.LightweightDepartment;

import java.util.List;

public interface DepartmentService {
    Department get(Long id);

    LightweightDepartment getLightweight(Long id);

    List<Department> getAll();
}
