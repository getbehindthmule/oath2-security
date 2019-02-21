package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Address;
import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.Employee;
import com.greenhills.oauth2security.dto.Office;
import com.greenhills.oauth2security.model.business.AddressEntity;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.model.business.EmployeeEntity;
import com.greenhills.oauth2security.model.business.OfficeEntity;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

public class DepartmentBuilderTest {
    final static String street = "Taits Lane";
    final static String houseNumber = "35";
    final static String zipCode = "DD6 9BW";

    final static String empoyeeName = "alan";
    final static String employeeSurname = "partridge";

    final static String officeName = "planning";

    final static String departmentName = "HR";

    private static AddressEntity getTestAddressEntity(Long id) {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet(street);
        addressEntity.setHouseNumber(houseNumber);
        addressEntity.setZipCode(zipCode);

        return addressEntity;
    }

    private static Address getTestAddress(Long id) {
        return Address.builder()
                .id(id)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();
    }

    private static OfficeEntity getTestOfficeEntity(Long id) {
        final OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(id);
        officeEntity.setName(officeName);
        officeEntity.setDepartment(new DepartmentEntity());
        officeEntity.setAddress(getTestAddressEntity(id));

        return officeEntity;
    }

    private static Office getTestOffice(Long id) {
        return Office.builder()
                .id(id)
                .name(officeName)
                .department(null)
                .address(getTestAddress(id))
                .build();
    }

    private static EmployeeEntity getTestEmployeeEntity(Long id) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(id);
        employeeEntity.setAddress(getTestAddressEntity(id));
        employeeEntity.setDepartment(null);
        employeeEntity.setName(empoyeeName);
        employeeEntity.setSurname(employeeSurname);

        return employeeEntity;
    }

    private static Employee getTestEmployee(Long id) {
        return Employee.builder()
                .id(id)
                .address(getTestAddress(id))
                .department(null)
                .name(empoyeeName)
                .surname(employeeSurname)
                .build();
    }


    private DepartmentEntity buildDefaultTestDepartmentEntity(){
        final DepartmentEntity departmentEntity = new DepartmentEntity();

        Set<OfficeEntity> officeEntities = LongStream.rangeClosed(1, 2)
                .mapToObj(DepartmentBuilderTest::getTestOfficeEntity)
                .map(office -> {
                    office.setDepartment(departmentEntity);
                    return office;
                })
                .collect(Collectors.toSet());

        Set<EmployeeEntity> employeeEntities = LongStream.rangeClosed(1, 2)
                .mapToObj(DepartmentBuilderTest::getTestEmployeeEntity)
                .map(employee -> {
                    employee.setDepartment(departmentEntity);
                    return employee;
                })
                .collect(Collectors.toSet());

        departmentEntity.setId(1L);
        departmentEntity.setName(departmentName);
        departmentEntity.setOffices(officeEntities);
        departmentEntity.setEmployees(employeeEntities);

        return departmentEntity;
    }

    private Department buildDefaultTestDepartment(){
        Department department = Department.builder()
                .id(1L)
                .name(departmentName)
                .company(null)
                .build();

        Set<Office> offices = LongStream.rangeClosed(1, 2)
                .mapToObj(DepartmentBuilderTest::getTestOffice)
                .map(office -> {
                    office.setDepartment(department);
                    return office;
                })
                .collect(Collectors.toSet());

        Set<Employee> employees = LongStream.rangeClosed(1, 2)
                .mapToObj(DepartmentBuilderTest::getTestEmployee)
                .map(employee -> {
                    employee.setDepartment(department);
                    return employee;
                })
                .collect(Collectors.toSet());


        department.setEmployees(employees);
        department.setOffices(offices);

        return department;
    }

    @Test
    public void testWhenMappingNull() {
        // arrange

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(null);

        // assert
        assertThat(actualDepartment.isPresent()).isFalse();
    }

    @Test
    public void testFullMapping() {
        // arrange
        final Department expectedDepartment = buildDefaultTestDepartment();

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(buildDefaultTestDepartmentEntity());

        // assert
        assertThat(actualDepartment.get()).isEqualTo(expectedDepartment);
        assertThat(actualDepartment.get().getEmployees())
                .extracting(Employee::getDepartment)
                .contains(expectedDepartment);

        assertThat(actualDepartment.get().getOffices())
                .extracting(Office::getDepartment)
                .contains(expectedDepartment);
    }

    @Test
    public void testNullOfficesAreHandled() {
        // arrange
        Department expectedDepartment = buildDefaultTestDepartment();
        expectedDepartment.setOffices(Collections.EMPTY_SET);
        DepartmentEntity departmentEntity = buildDefaultTestDepartmentEntity();
        departmentEntity.setOffices(null);

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(departmentEntity);

        // assert
        assertThat(actualDepartment.get()).isEqualTo(expectedDepartment);
        assertThat(actualDepartment.get().getOffices()).hasSize(0);

    }

    @Test
    public void testNullEmployeesAreHandled() {
        // arrange
        Department expectedDepartment = buildDefaultTestDepartment();
        expectedDepartment.setEmployees(Collections.EMPTY_SET);
        DepartmentEntity departmentEntity = buildDefaultTestDepartmentEntity();
        departmentEntity.setEmployees(null);

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(departmentEntity);

        // assert
        assertThat(actualDepartment.get()).isEqualTo(expectedDepartment);
        assertThat(actualDepartment.get().getEmployees()).hasSize(0);

    }

    @Test
    public void testEmptyCollectionOfOfficesAreHandled() {
        // arrange
        Department expectedDepartment = buildDefaultTestDepartment();
        expectedDepartment.setOffices(Collections.EMPTY_SET);
        DepartmentEntity departmentEntity = buildDefaultTestDepartmentEntity();
        departmentEntity.setOffices(Collections.EMPTY_SET);

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(departmentEntity);

        // assert
        assertThat(actualDepartment.get()).isEqualTo(expectedDepartment);
        assertThat(actualDepartment.get().getOffices()).hasSize(0);

    }

    @Test
    public void testEmptyCollectionOfEmployeesAreHandled() {
        // arrange
        Department expectedDepartment = buildDefaultTestDepartment();
        expectedDepartment.setEmployees(Collections.EMPTY_SET);
        DepartmentEntity departmentEntity = buildDefaultTestDepartmentEntity();
        departmentEntity.setEmployees(Collections.EMPTY_SET);

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(departmentEntity);

        // assert
        assertThat(actualDepartment.get()).isEqualTo(expectedDepartment);
        assertThat(actualDepartment.get().getEmployees()).hasSize(0);

    }

    @Test
    public void testExcludesNullOfficeIsExcluded() {
        // arrange
        final OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(99L);
        final Office office = Office.builder().id(99L).build();
        Set<OfficeEntity> officeEntities = new HashSet<OfficeEntity>() {{
            add(null);
            add(officeEntity);
        }};
        Set<Office> offices = new HashSet<Office>() {{
            add(office);
        }};
        Department expectedDepartment = buildDefaultTestDepartment();
        expectedDepartment.setOffices(offices);
        DepartmentEntity departmentEntity = buildDefaultTestDepartmentEntity();
        departmentEntity.setOffices(officeEntities);

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(departmentEntity);

        // assert
        assertThat(actualDepartment.get()).isEqualTo(expectedDepartment);
        assertThat(actualDepartment.get().getOffices()).hasSize(1);

    }

    @Test
    public void testExcludesNullEmployeeIsExcluded() {
        // arrange
        final EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(99L);
        final Employee employee = Employee.builder().id(99L).build();
        Set<EmployeeEntity> employeeEntities = new HashSet<EmployeeEntity>() {{
            add(null);
            add(employeeEntity);
        }};
        Set<Employee> employees = new HashSet<Employee>() {{
            add(employee);
        }};
        Department expectedDepartment = buildDefaultTestDepartment();
        expectedDepartment.setEmployees(employees);
        DepartmentEntity departmentEntity = buildDefaultTestDepartmentEntity();
        departmentEntity.setEmployees(employeeEntities);

        // act
        Optional<Department> actualDepartment = DepartmentBuilder.departmentFromEntity(departmentEntity);

        // assert
        assertThat(actualDepartment.get()).isEqualTo(expectedDepartment);
        assertThat(actualDepartment.get().getEmployees()).hasSize(1);

    }

}