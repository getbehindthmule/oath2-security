package com.greenhills.oauth2security.dto.builder;


import com.greenhills.oauth2security.dto.*;
import com.greenhills.oauth2security.model.business.*;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.LongStream;

import static java.util.stream.Collectors.toSet;
import static org.assertj.core.api.Assertions.assertThat;

public class CompanyBuilderTest {

    final static String street = "Taits Lane";
    final static String houseNumber = "35";
    final static String zipCode = "DD6 9BW";

    final static String employeeName = "alan";
    final static String employeeSurname = "partridge";

    final static String officeName = "planning";

    final static String departmentName = "HR";

    final static String reg = "YC 12 EOT";

    final static String companyName = "Green Hills";

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
        employeeEntity.setName(employeeName);
        employeeEntity.setSurname(employeeSurname);

        return employeeEntity;
    }

    private static Employee getTestEmployee(Long id) {
        return Employee.builder()
                .id(id)
                .address(getTestAddress(id))
                .department(null)
                .name(employeeName)
                .surname(employeeSurname)
                .build();
    }

    private static CarEntity getTestCarEntity(Long id) {
        CarEntity carEntity = new CarEntity();
        carEntity.setId(id);
        carEntity.setRegistrationNumber(reg);
        return carEntity;
    }

    private static Car getTestCar(Long id) {
        return Car.builder()
                .id(id)
                .registrationNumber(reg)
                .build();
    }


    private static DepartmentEntity getTestDepartmentEntity(Long id) {
        final DepartmentEntity departmentEntity = new DepartmentEntity();

        Set<OfficeEntity> officeEntities = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderTest::getTestOfficeEntity)
                .map(office -> {
                    office.setDepartment(departmentEntity);
                    return office;
                })
                .collect(toSet());

        Set<EmployeeEntity> employeeEntities = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderTest::getTestEmployeeEntity)
                .map(employee -> {
                    employee.setDepartment(departmentEntity);
                    return employee;
                })
                .collect(toSet());

        departmentEntity.setId(id);
        departmentEntity.setName(departmentName);
        departmentEntity.setOffices(officeEntities);
        departmentEntity.setEmployees(employeeEntities);

        return departmentEntity;
    }

    private static Department getTestDepartment(Long id) {
        Department department = Department.builder()
                .id(id)
                .name(departmentName)
                .company(null)
                .build();

        Set<Office> offices = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderTest::getTestOffice)
                .map(office -> {
                    office.setDepartment(department);
                    return office;
                })
                .collect(toSet());

        Set<Employee> employees = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderTest::getTestEmployee)
                .map(employee -> {
                    employee.setDepartment(department);
                    return employee;
                })
                .collect(toSet());


        department.setEmployees(employees);
        department.setOffices(offices);

        return department;
    }

    private static CompanyEntity buildDefaultCompanyEntity() {
        CompanyEntity companyEntity = new CompanyEntity();
        Set<CarEntity> carEntities = LongStream.rangeClosed(1, 10)
                .mapToObj(CompanyBuilderTest::getTestCarEntity)
                .map(car -> {
                    car.setCompany(companyEntity);
                    return car;
                })
                .collect(toSet());

        Set<DepartmentEntity> departmentEntities = LongStream.rangeClosed(1, 5)
                .mapToObj(CompanyBuilderTest::getTestDepartmentEntity)
                .map(dept -> {
                    dept.setCompany(companyEntity);
                    return dept;
                })
                .collect(toSet());

        companyEntity.setId(1L);
        companyEntity.setName(companyName);
        companyEntity.setCars(carEntities);
        companyEntity.setDepartments(departmentEntities);
        return companyEntity;
    }

    private static Company buildDefaultCompany() {
        Company company = Company.builder()
                .id(1L)
                .name(companyName)
                .build();

        Set<Car> cars = LongStream.rangeClosed(1, 10)
                .mapToObj(CompanyBuilderTest::getTestCar)
                .map(car -> {
                    car.setCompany(company);
                    return car;
                })
                .collect(toSet());

        Set<Department> departments = LongStream.rangeClosed(1, 5)
                .mapToObj(CompanyBuilderTest::getTestDepartment)
                .map(dept -> {
                    dept.setCompany(company);
                    return dept;
                })
                .collect(toSet());

        company.setCars(cars);
        company.setDepartments(departments);

        return company;
    }

    @Test
    public void testWhenMappingNull() {
        // arrange

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(null);

        // assert
        assertThat(actualCompany.isPresent()).isFalse();
    }

    @Test
    public void testFullMapping() {
        // arrange
        CompanyEntity companyEntity = buildDefaultCompanyEntity();
        Company expectedCompany = buildDefaultCompany();

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCars())
                .extracting(Car::getCompany)
                .contains(expectedCompany);
        assertThat(actualCompany.get().getDepartments())
                .extracting(Department::getCompany)
                .contains(expectedCompany);
    }

    @Test
    public void testExcludesNullCarEntriesFromEntity() {
        // arrange
        CompanyEntity companyEntity = buildDefaultCompanyEntity();
        Company expectedCompany = buildDefaultCompany();

        Set<CarEntity> carEntities = new HashSet<CarEntity>() {{
            add(getTestCarEntity(1L));
            add(null);
        }};

        Set<Car> cars = new HashSet<Car>() {{
            add(getTestCar(1L));
        }};

        companyEntity.setCars(carEntities);
        expectedCompany.setCars(cars);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
    }

    @Test
    public void testExcludesNullDeptEntriesFromEntity() {
        // arrange
        CompanyEntity companyEntity = buildDefaultCompanyEntity();
        Company expectedCompany = buildDefaultCompany();

        Set<DepartmentEntity> departmentEntities = new HashSet<DepartmentEntity>() {{
            add(getTestDepartmentEntity(1L));
            add(null);
        }};

        Set<Department> departments = new HashSet<Department>() {{
            add(getTestDepartment(1L));
        }};

        companyEntity.setDepartments(departmentEntities);
        expectedCompany.setDepartments(departments);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
    }

    @Test
    public void testNullCarsAreHandled() {
        // arrange
        CompanyEntity companyEntity = buildDefaultCompanyEntity();
        companyEntity.setCars(null);
        Company expectedCompany = buildDefaultCompany();
        expectedCompany.setCars(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
    }

    @Test
    public void testNullDeptsAreHandled() {
        // arrange
        CompanyEntity companyEntity = buildDefaultCompanyEntity();
        companyEntity.setDepartments(null);
        Company expectedCompany = buildDefaultCompany();
        expectedCompany.setDepartments(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
    }

    @Test
    public void testEmptyCollectionsOfCarsAreHandled() {
        // arrange
        CompanyEntity companyEntity = buildDefaultCompanyEntity();
        companyEntity.setCars(Collections.EMPTY_SET);
        Company expectedCompany = buildDefaultCompany();
        expectedCompany.setCars(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
    }

    @Test
    public void testEmptyCollectionsOfDeptsAreHandled() {
        // arrange
        CompanyEntity companyEntity = buildDefaultCompanyEntity();
        companyEntity.setDepartments(Collections.EMPTY_SET);
        Company expectedCompany = buildDefaultCompany();
        expectedCompany.setDepartments(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
    }

}