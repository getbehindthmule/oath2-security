package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Address;
import com.greenhills.oauth2security.dto.Employee;
import com.greenhills.oauth2security.model.business.AddressEntity;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.model.business.EmployeeEntity;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class EmployeeBuilderTest {
    final Long id = 2L;
    final String street = "Taits Lane";
    final String houseNumber = "35";
    final String zipCode = "DD6 9BW";
    final String name = "alan";
    final String surname = "partridge";

    private AddressEntity getTestAddressEntity() {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet(street);
        addressEntity.setHouseNumber(houseNumber);
        addressEntity.setZipCode(zipCode);

        return addressEntity;
    }

    private Address getTestAddress() {
        return Address.builder()
                .id(id)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();
    }

    @Test
    public void testWhenMappingNull() {
        // arrange

        // act
        Optional<Employee> actualEmployee = EmployeeBuilder.employeeFromEntity(null);

        // assert
        assertThat(actualEmployee.isPresent()).isFalse();
    }

    @Test
    public void testWhenMappingIsPresent() {
        // arrange
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(id);
        employeeEntity.setAddress(getTestAddressEntity());
        employeeEntity.setDepartment(new DepartmentEntity());
        employeeEntity.setName(name);
        employeeEntity.setSurname(surname);

        Employee expectedEmployee = Employee.builder()
                .id(id)
                .address(getTestAddress())
                .department(null)
                .name(name)
                .surname(surname)
                .build();

        // act
        Optional<Employee> actualEmployee = EmployeeBuilder.employeeFromEntity(employeeEntity);

        // assert
        assertThat(actualEmployee.get()).isEqualTo(expectedEmployee);
    }

    @Test
    public void testWhenAddressIsMissing() {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(id);
        employeeEntity.setAddress(null);
        employeeEntity.setDepartment(new DepartmentEntity());
        employeeEntity.setName(name);
        employeeEntity.setSurname(surname);

        Employee expectedEmployee = Employee.builder()
                .id(id)
                .address(null)
                .department(null)
                .name(name)
                .surname(surname)
                .build();

        // act
        Optional<Employee> actualEmployee = EmployeeBuilder.employeeFromEntity(employeeEntity);

        // assert
        assertThat(actualEmployee.get()).isEqualTo(expectedEmployee);
    }

}