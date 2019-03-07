package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.*;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.model.business.EmployeeEntity;
import com.greenhills.oauth2security.model.business.OfficeEntity;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class DepartmentBuilder {


    public static Optional<Department> departmentFromEntity(DepartmentEntity departmentEntity ) {
        if (departmentEntity == null) return Optional.empty();

        Set<Employee> employees = Optional.ofNullable(departmentEntity.getEmployees()).orElse(new HashSet<>())
                .stream()
                .map(EmployeeBuilder::employeeFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        Set<Office> offices = Optional.ofNullable(departmentEntity.getOffices()).orElse(new HashSet<>())
                .stream()
                .map(OfficeBuilder::officeFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        Department department = Department.builder()
                .id(departmentEntity.getId())
                .name(departmentEntity.getName())
                .employees(employees)
                .offices(offices)
                .build();

        department.getEmployees().forEach(employee -> employee.setDepartment(department));
        department.getOffices().forEach(office -> office.setDepartment(department));

        return Optional.of(department);
    }

    public static Optional<LightweightDepartment> lightweightDepartmentFromEntity(DepartmentEntity departmentEntity ) {
        if (departmentEntity == null) return Optional.empty();

        Set<Long> employees = Optional.ofNullable(departmentEntity.getEmployees()).orElse(new HashSet<>())
                .stream()
                .filter(Objects::nonNull)
                .map(EmployeeEntity::getId)
                .collect(Collectors.toSet());

        Set<Long> offices = Optional.ofNullable(departmentEntity.getOffices()).orElse(new HashSet<>())
                .stream()
                .filter(Objects::nonNull)
                .map(OfficeEntity::getId)
                .collect(Collectors.toSet());

        LightweightDepartment department = LightweightDepartment.builder().build().builder()
                .id(departmentEntity.getId())
                .name(departmentEntity.getName())
                .employeeIds(employees)
                .officeIds(offices)
                .companyId(departmentEntity.getCompany().getId())
                .build();


        return Optional.of(department);
    }

    public static Optional<DepartmentEntity> entityFromDepartment(Department department) {
        if (department == null) return Optional.empty();

        Set<EmployeeEntity> employeeEntities = Optional.ofNullable(department.getEmployees()).orElse(new HashSet<>())
                .stream()
                .map(EmployeeBuilder::entityFromEmployee)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        Set<OfficeEntity> offices = Optional.ofNullable(department.getOffices()).orElse(new HashSet<>())
                .stream()
                .map(OfficeBuilder::entityFromOffice)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(department.getId());
        departmentEntity.setName(department.getName());
        departmentEntity.setEmployees(employeeEntities);
        departmentEntity.setOffices(offices);

        departmentEntity.getEmployees().forEach(employee -> employee.setDepartment(departmentEntity));
        departmentEntity.getOffices().forEach(office -> office.setDepartment(departmentEntity));

        return Optional.of(departmentEntity);
    }

    public static Optional<DepartmentEntity> entityFromLightweightDepartmen(LightweightDepartment department) {
        if (department == null) return Optional.empty();

        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(department.getId());
        departmentEntity.setName(department.getName());
        departmentEntity.setEmployees(Collections.EMPTY_SET);
        departmentEntity.setOffices(Collections.EMPTY_SET);

        return Optional.of(departmentEntity);
    }
}
