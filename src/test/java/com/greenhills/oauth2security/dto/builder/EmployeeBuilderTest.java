package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Address;
import com.greenhills.oauth2security.dto.Employee;
import com.greenhills.oauth2security.dto.LightweightEmployee;
import com.greenhills.oauth2security.model.business.AddressEntity;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.model.business.EmployeeEntity;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "SpellCheckingInspection", "WeakerAccess"})
public class EmployeeBuilderTest {
    private final Long id = 2L;
    private final Long deptId = 3L;
    private final String street = "Taits Lane";
    private final String houseNumber = "35";
    private final String zipCode = "DD6 9BW";
    private final String name = "alan";
    private final String surname = "partridge";

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
    public void testLightweightWhenMappingNull() {
        // arrange

        // act
        Optional<LightweightEmployee> actualEmployee = EmployeeBuilder.lightweightEmployeeFromEntity(null);

        // assert
        assertThat(actualEmployee.isPresent()).isFalse();
    }

    @Test
    public void testWhenEntityMappingNull() {
        // arrange

        // act
        Optional<EmployeeEntity> actualEmployee = EmployeeBuilder.entityFromEmployee(null);

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
    public void testLightweightWhenMappingIsPresent() {
        // arrange
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(deptId);
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(id);
        employeeEntity.setAddress(getTestAddressEntity());
        employeeEntity.setDepartment(new DepartmentEntity());
        employeeEntity.setName(name);
        employeeEntity.setSurname(surname);
        employeeEntity.setDepartment(departmentEntity);

        LightweightEmployee expectedEmployee = LightweightEmployee.builder()
                .id(id)
                .address(getTestAddress())
                .departmentId(deptId)
                .name(name)
                .surname(surname)
                .build();

        // act
        Optional<LightweightEmployee> actualEmployee = EmployeeBuilder.lightweightEmployeeFromEntity(employeeEntity);

        // assert
        assertThat(actualEmployee.get()).isEqualTo(expectedEmployee);
    }

    @Test
    public void testWhenEntityMappingIsPresent() {
        // arrange
        EmployeeEntity expectedEmployee = new EmployeeEntity();
        expectedEmployee.setId(id);
        expectedEmployee.setAddress(getTestAddressEntity());
        expectedEmployee.setDepartment(null);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);

        Employee employee = Employee.builder()
                .id(id)
                .address(getTestAddress())
                .department(null)
                .name(name)
                .surname(surname)
                .build();

        // act
        Optional<EmployeeEntity> actualEmployee = EmployeeBuilder.entityFromEmployee(employee);

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

    @Test
    public void testLightweightWhenAddressIsMissing() {
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(deptId);
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(id);
        employeeEntity.setAddress(null);
        employeeEntity.setDepartment(new DepartmentEntity());
        employeeEntity.setName(name);
        employeeEntity.setSurname(surname);
        employeeEntity.setDepartment(departmentEntity);

        LightweightEmployee expectedEmployee = LightweightEmployee.builder()
                .id(id)
                .address(null)
                .departmentId(deptId)
                .name(name)
                .surname(surname)
                .build();

        // act
        Optional<LightweightEmployee> actualEmployee = EmployeeBuilder.lightweightEmployeeFromEntity(employeeEntity);

        // assert
        assertThat(actualEmployee.get()).isEqualTo(expectedEmployee);
    }

    @Test
    public void testWhenEntityAddressIsMissing() {
        EmployeeEntity expectedEmployee = new EmployeeEntity();
        expectedEmployee.setId(id);
        expectedEmployee.setAddress(null);
        expectedEmployee.setDepartment(null);
        expectedEmployee.setName(name);
        expectedEmployee.setSurname(surname);

        Employee employee = Employee.builder()
                .id(id)
                .address(null)
                .department(null)
                .name(name)
                .surname(surname)
                .build();

        // act
        Optional<EmployeeEntity> actualEmployee = EmployeeBuilder.entityFromEmployee(employee);

        // assert
        assertThat(actualEmployee.get()).isEqualTo(expectedEmployee);
    }

}