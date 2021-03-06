package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Employee;
import com.greenhills.oauth2security.dto.LightweightEmployee;
import com.greenhills.oauth2security.model.business.EmployeeEntity;

import java.util.Optional;

@SuppressWarnings("WeakerAccess")
public class EmployeeBuilder {
    static Optional<Employee> employeeFromEntity(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) return Optional.empty();

        return Optional.of(
                Employee.builder()
                        .id(employeeEntity.getId())
                        .name(employeeEntity.getName())
                        .surname(employeeEntity.getSurname())
                        .address(AddressBuilder.addressFromEntity(employeeEntity.getAddress()).orElse(null))
                        .build()
        );
    }

    static Optional<LightweightEmployee> lightweightEmployeeFromEntity(EmployeeEntity employeeEntity) {
        if (employeeEntity == null) return Optional.empty();

        return Optional.of(
                LightweightEmployee.builder()
                        .id(employeeEntity.getId())
                        .name(employeeEntity.getName())
                        .surname(employeeEntity.getSurname())
                        .address(AddressBuilder.addressFromEntity(employeeEntity.getAddress()).orElse(null))
                        .departmentId(employeeEntity.getDepartment().getId())
                        .build()
        );
    }

    static Optional<EmployeeEntity> entityFromEmployee(Employee employee) {
        if (employee == null) return Optional.empty();

        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(employee.getId());
        employeeEntity.setName(employee.getName());
        employeeEntity.setSurname(employee.getSurname());
        employeeEntity.setAddress(AddressBuilder.entityFromAddress(employee.getAddress()).orElse(null));

        return Optional.of(employeeEntity);
    }
}
