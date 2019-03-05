package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.*;
import com.greenhills.oauth2security.model.business.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class CompanyBuilderUtil {
    final static String street = "Taits Lane";
    final static String houseNumber = "35";
    final static String zipCode = "DD6 9BW";

    final static String employeeName = "alan";
    final static String employeeSurname = "partridge";

    final static String officeName = "planning";

    final static String departmentName = "HR";

    final static String reg = "YC 12 EOT";

    final static String companyName = "Green Hills";

    static public AddressEntity getTestAddressEntity(Long id) {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet(street);
        addressEntity.setHouseNumber(houseNumber);
        addressEntity.setZipCode(zipCode);

        return addressEntity;
    }

    static public Address getTestAddress(Long id) {
        return Address.builder()
                .id(id)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();
    }

    static public OfficeEntity getTestOfficeEntity(Long id) {
        final OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(id);
        officeEntity.setName(officeName);
        officeEntity.setDepartment(new DepartmentEntity());
        officeEntity.setAddress(getTestAddressEntity(id));

        return officeEntity;
    }

    static public Office getTestOffice(Long id) {
        return Office.builder()
                .id(id)
                .name(officeName)
                .department(null)
                .address(getTestAddress(id))
                .build();
    }

    static public EmployeeEntity getTestEmployeeEntity(Long id) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.setId(id);
        employeeEntity.setAddress(getTestAddressEntity(id));
        employeeEntity.setDepartment(null);
        employeeEntity.setName(employeeName);
        employeeEntity.setSurname(employeeSurname);

        return employeeEntity;
    }

    static public Employee getTestEmployee(Long id) {
        return Employee.builder()
                .id(id)
                .address(getTestAddress(id))
                .department(null)
                .name(employeeName)
                .surname(employeeSurname)
                .build();
    }

    static public CarEntity getTestCarEntity(Long id) {
        CarEntity carEntity = new CarEntity();
        carEntity.setId(id);
        carEntity.setRegistrationNumber(reg);
        return carEntity;
    }

    static public Car getTestCar(Long id) {
        return Car.builder()
                .id(id)
                .registrationNumber(reg)
                .build();
    }


    static public DepartmentEntity getTestDepartmentEntity(Long id) {
        final DepartmentEntity departmentEntity = new DepartmentEntity();

        Set<OfficeEntity> officeEntities = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderUtil::getTestOfficeEntity)
                .map(office -> {
                    office.setDepartment(departmentEntity);
                    return office;
                })
                .collect(toSet());

        Set<EmployeeEntity> employeeEntities = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderUtil::getTestEmployeeEntity)
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

    static public Department getTestDepartment(Long id) {
        Department department = Department.builder()
                .id(id)
                .name(departmentName)
                .company(null)
                .build();

        Set<Office> offices = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderUtil::getTestOffice)
                .map(office -> {
                    office.setDepartment(department);
                    return office;
                })
                .collect(toSet());

        Set<Employee> employees = LongStream.rangeClosed(1, 2)
                .mapToObj(CompanyBuilderUtil::getTestEmployee)
                .map(employee -> {
                    employee.setDepartment(department);
                    return employee;
                })
                .collect(toSet());


        department.setEmployees(employees);
        department.setOffices(offices);

        return department;
    }

    static public CompanyEntity buildDefaultCompanyEntity() {
        CompanyEntity companyEntity = new CompanyEntity();
        Set<CarEntity> carEntities = LongStream.rangeClosed(1, 10)
                .mapToObj(CompanyBuilderUtil::getTestCarEntity)
                .map(car -> {
                    car.setCompany(companyEntity);
                    return car;
                })
                .collect(toSet());

        Set<DepartmentEntity> departmentEntities = LongStream.rangeClosed(1, 5)
                .mapToObj(CompanyBuilderUtil::getTestDepartmentEntity)
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

    static public Company buildDefaultCompany() {
        Company company = Company.builder()
                .id(1L)
                .name(companyName)
                .build();

        Set<Car> cars = LongStream.rangeClosed(1, 10)
                .mapToObj(CompanyBuilderUtil::getTestCar)
                .map(car -> {
                    car.setCompany(company);
                    return car;
                })
                .collect(toSet());

        Set<Department> departments = LongStream.rangeClosed(1, 5)
                .mapToObj(CompanyBuilderUtil::getTestDepartment)
                .map(dept -> {
                    dept.setCompany(company);
                    return dept;
                })
                .collect(toSet());

        company.setCars(cars);
        company.setDepartments(departments);

        return company;
    }

    static public LightweightCompany buildDefaultLightweightCompany() {
        LightweightCompany company = LightweightCompany.builder()
                .id(1L)
                .name(companyName)
                .build();

        Set<Long> cars = Stream.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L)
                .collect(Collectors.toCollection(HashSet::new));

        Set<Long> departments = Stream.of(1L, 2L, 3L, 4L, 5L)
                .collect(Collectors.toCollection(HashSet::new));

        company.setCarIds(cars);
        company.setDepartmentIds(departments);

        return company;
    }

    static public LightweightDepartment buildDefaultLightweightDepartment() {
        LightweightDepartment department = LightweightDepartment.builder()
                .id(1L)
                .name(departmentName)
                .companyId(1L)
                .build();


        Set<Long> employees = Stream.of(1L, 2L)
                .collect(Collectors.toCollection(HashSet::new));
        Set<Long> offices = Stream.of(1L, 2L)
                .collect(Collectors.toCollection(HashSet::new));

        department.setEmployeeIds(employees);
        department.setOfficeIds(offices);

        return department;
    }

}
