package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.Employee;
import com.greenhills.oauth2security.dto.Office;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.model.business.EmployeeEntity;
import com.greenhills.oauth2security.model.business.OfficeEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class DepartmentBuilder {

    static Optional<Department> departmentFromEntity(DepartmentEntity departmentEntity ) {
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
}
