package com.greenhills.oauth2security.dto.builder;


import com.greenhills.oauth2security.dto.*;
import com.greenhills.oauth2security.model.business.*;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class CompanyBuilderTest  {



    @Test
    public void testWhenMappingNull() {
        // arrange

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(null);

        // assert
        assertThat(actualCompany.isPresent()).isFalse();
    }

    @Test
    public void testEntityWhenMappingNull() {
        // arrange

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(null);

        // assert
        assertThat(actualCompany.isPresent()).isFalse();
    }

    @Test
    public void testFullMapping() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();

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
    public void testEntityFullMapping() {
        // arrange
        CompanyEntity expectedCompany = CompanyBuilderUtil.buildDefaultCompanyEntity();
        Company company = CompanyBuilderUtil.buildDefaultCompany();

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(company);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCars())
                .extracting(CarEntity::getCompany)
                .contains(expectedCompany);
        assertThat(actualCompany.get().getDepartments())
                .extracting(DepartmentEntity::getCompany)
                .contains(expectedCompany);
    }

    @Test
    public void testExcludesNullCarEntriesFromEntity() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();

        Set<CarEntity> carEntities = new HashSet<CarEntity>() {{
            add(CompanyBuilderUtil.getTestCarEntity(1L));
            add(null);
        }};

        Set<Car> cars = new HashSet<Car>() {{
            add(CompanyBuilderUtil.getTestCar(1L));
        }};

        companyEntity.setCars(carEntities);
        expectedCompany.setCars(cars);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCars()).hasSize(1);
    }

    @Test
    public void testEntityExcludesNullCarEntriesFromEntity() {
        // arrange
        CompanyEntity expectedCompany = CompanyBuilderUtil.buildDefaultCompanyEntity();
        Company company = CompanyBuilderUtil.buildDefaultCompany();

        Set<CarEntity> carEntities = new HashSet<CarEntity>() {{
            add(CompanyBuilderUtil.getTestCarEntity(1L));
        }};

        Set<Car> cars = new HashSet<Car>() {{
            add(CompanyBuilderUtil.getTestCar(1L));
            add(null);
        }};

        expectedCompany.setCars(carEntities);
        company.setCars(cars);

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(company);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCars()).hasSize(1);
    }

    @Test
    public void testExcludesNullDeptEntriesFromEntity() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();

        Set<DepartmentEntity> departmentEntities = new HashSet<DepartmentEntity>() {{
            add(CompanyBuilderUtil.getTestDepartmentEntity(1L));
            add(null);
        }};

        Set<Department> departments = new HashSet<Department>() {{
            add(CompanyBuilderUtil.getTestDepartment(1L));
        }};

        companyEntity.setDepartments(departmentEntities);
        expectedCompany.setDepartments(departments);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartments()).hasSize(1);
    }

    @Test
    public void testEntityExcludesNullDeptEntriesFromEntity() {
        // arrange
        CompanyEntity expectedCompany = CompanyBuilderUtil.buildDefaultCompanyEntity();
        Company company = CompanyBuilderUtil.buildDefaultCompany();

        Set<DepartmentEntity> departmentEntities = new HashSet<DepartmentEntity>() {{
            add(CompanyBuilderUtil.getTestDepartmentEntity(1L));
        }};

        Set<Department> departments = new HashSet<Department>() {{
            add(null);
            add(CompanyBuilderUtil.getTestDepartment(1L));
        }};

        expectedCompany.setDepartments(departmentEntities);
        company.setDepartments(departments);

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(company);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartments()).hasSize(1);
    }

    @Test
    public void testNullCarsAreHandled() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setCars(null);
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        expectedCompany.setCars(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
    }

    @Test
    public void testEntityNullCarsAreHandled() {
        // arrange
        CompanyEntity expectedCompany = CompanyBuilderUtil.buildDefaultCompanyEntity();
        expectedCompany.setCars(Collections.EMPTY_SET);
        Company company = CompanyBuilderUtil.buildDefaultCompany();
        company.setCars(null);

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(company);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCars()).hasSize(0);
    }



    @Test
    public void testNullDeptsAreHandled() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setDepartments(null);
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        expectedCompany.setDepartments(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartments()).hasSize(0);
    }

    @Test
    public void testEntityNullDeptsAreHandled() {
        // arrange
        CompanyEntity expectedCompany = CompanyBuilderUtil.buildDefaultCompanyEntity();
        expectedCompany.setDepartments(Collections.EMPTY_SET);
        Company company = CompanyBuilderUtil.buildDefaultCompany();
        company.setDepartments(null);

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(company);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartments()).hasSize(0);
    }

    @Test
    public void testEmptyCollectionsOfCarsAreHandled() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setCars(Collections.EMPTY_SET);
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        expectedCompany.setCars(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCars()).hasSize(0);
    }

    @Test
    public void testEntityEmptyCollectionsOfCarsAreHandled() {
        // arrange
        CompanyEntity expectedCompany = CompanyBuilderUtil.buildDefaultCompanyEntity();
        expectedCompany.setCars(Collections.EMPTY_SET);
        Company company = CompanyBuilderUtil.buildDefaultCompany();
        company.setCars(Collections.EMPTY_SET);

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(company);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCars()).hasSize(0);
    }

    @Test
    public void testEmptyCollectionsOfDeptsAreHandled() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setDepartments(Collections.EMPTY_SET);
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        expectedCompany.setDepartments(Collections.EMPTY_SET);

        // act
        Optional<Company> actualCompany = CompanyBuilder.companyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartments()).hasSize(0);
    }

    @Test
    public void testEntityEmptyCollectionsOfDeptsAreHandled() {
        // arrange
        CompanyEntity expectedCompany = CompanyBuilderUtil.buildDefaultCompanyEntity();
        expectedCompany.setDepartments(Collections.EMPTY_SET);
        Company company = CompanyBuilderUtil.buildDefaultCompany();
        company.setDepartments(Collections.EMPTY_SET);

        // act
        Optional<CompanyEntity> actualCompany = CompanyBuilder.entityFromCompany(company);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartments()).hasSize(0);
    }

}