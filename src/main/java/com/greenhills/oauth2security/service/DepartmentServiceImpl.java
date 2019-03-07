package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.annotations.SecureRead;
import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.LightweightDepartment;
import com.greenhills.oauth2security.dto.builder.DepartmentBuilder;
import com.greenhills.oauth2security.model.business.CompanyEntity;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.repository.CompanyRepository;
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
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;

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

    @Override
    @SecureRead
    @PreAuthorize("hasAuthority('DEPARTMENT_CREATE') and hasAuthority('COMPANY_READ')")
    @Transactional
    public Optional<Long> create(LightweightDepartment department) {
        if (department == null) return Optional.empty();

        CompanyEntity companyEntity = companyRepository.findCompanyEntityById(department.getCompanyId());
        Optional<DepartmentEntity> departmentEntity = DepartmentBuilder.entityFromLightweightDepartmen(department);
        if ((companyEntity == null) || !departmentEntity.isPresent()) return Optional.empty();

        departmentEntity.get().setCompany(companyEntity);
        companyEntity.getDepartments().add(departmentEntity.get());

        CompanyEntity savedCompanyEntity = companyRepository.save(companyEntity);

        Optional<DepartmentEntity> savedDepartmentEntity = savedCompanyEntity.getDepartments().stream()
                .filter(d -> d.getName() == department.getName() )
                .findFirst();


        return savedDepartmentEntity.map(DepartmentEntity::getId);
    }
}
