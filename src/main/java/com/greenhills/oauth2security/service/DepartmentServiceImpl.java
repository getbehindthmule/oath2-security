package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.annotations.SecureRead;
import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.LightweightDepartment;
import com.greenhills.oauth2security.dto.builder.DepartmentBuilder;
import com.greenhills.oauth2security.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class DepartmentServiceImpl implements DepartmentService{
    @Autowired
    DepartmentRepository departmentRepository;

    @SecureRead
    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_READ')")
    @Transactional
    public Department get(Long id) {
        return DepartmentBuilder.departmentFromEntity(departmentRepository.findDepartmentEntitiesById(id)).orElse(null);
    }

    @SecureRead
    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_READ')")
    @Transactional
    public LightweightDepartment getLightweight(Long id) {
        return DepartmentBuilder.lightweightDepartmentFromEntity(departmentRepository.findDepartmentEntitiesById(id)).orElse(null);
    }


    @Override
    @PreAuthorize("hasAuthority('DEPARTMENT_READ')")
    @Transactional
    public List<Department> getAll() {
        return StreamSupport.stream(departmentRepository.findAll().spliterator(), false)
                .map(DepartmentBuilder::departmentFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}