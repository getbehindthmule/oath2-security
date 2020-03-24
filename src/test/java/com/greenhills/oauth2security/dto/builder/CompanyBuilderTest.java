package com.greenhills.oauth2security.dto.builder;


import com.greenhills.oauth2security.dto.Car;
import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.LightweightCompany;
import com.greenhills.oauth2security.model.business.CarEntity;
import com.greenhills.oauth2security.model.business.CompanyEntity;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent", "WeakerAccess"})
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
    public void testLightweightWhenMappingNull() {
        // arrange

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(null);

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
    public void testFullLightweightMapping() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
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
    public void testLightweightExcludesNullCarEntriesFromEntity() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();

        Set<CarEntity> carEntities = new HashSet<CarEntity>() {{
            add(CompanyBuilderUtil.getTestCarEntity(1L));
            add(null);
        }};

        Set<Long> cars = Collections.singleton(1L);

        companyEntity.setCars(carEntities);
        expectedCompany.setCarIds(cars);

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCarIds()).hasSize(1).contains(1L);
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
    public void testLightweightExcludesNullDeptEntriesFromEntity() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();

        Set<DepartmentEntity> departmentEntities = new HashSet<DepartmentEntity>() {{
            add(CompanyBuilderUtil.getTestDepartmentEntity(1L));
            add(null);
        }};

        Set<Long> departments = Collections.singleton(1L);

        companyEntity.setDepartments(departmentEntities);
        expectedCompany.setDepartmentIds(departments);

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartmentIds()).hasSize(1).contains(1L);
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
    public void testNullCarsAreHandled_ForLightweight() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setCars(null);
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();
        expectedCompany.setCarIds(Collections.EMPTY_SET);

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(companyEntity);

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
    public void testNullDeptsAreHandled_ForLightweight() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setDepartments(null);
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();
        expectedCompany.setDepartmentIds(Collections.EMPTY_SET);

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartmentIds()).hasSize(0);
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
    public void testEmptyCollectionsOfCarsAreHandledForLightweights() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setCars(Collections.EMPTY_SET);
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();
        expectedCompany.setCarIds(Collections.EMPTY_SET);

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getCarIds()).hasSize(0);
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
    public void testEmptyCollectionsOfDeptsAreHandledForLightweight() {
        // arrange
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setDepartments(Collections.EMPTY_SET);
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();
        expectedCompany.setDepartmentIds(Collections.EMPTY_SET);

        // act
        Optional<LightweightCompany> actualCompany = CompanyBuilder.lightweightCompanyFromEntity(companyEntity);

        // assert
        assertThat(actualCompany.get()).isEqualTo(expectedCompany);
        assertThat(actualCompany.get().getDepartmentIds()).hasSize(0);
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