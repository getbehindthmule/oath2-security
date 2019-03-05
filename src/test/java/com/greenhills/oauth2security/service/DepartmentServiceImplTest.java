package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.dto.LightweightDepartment;
import com.greenhills.oauth2security.dto.builder.CompanyBuilderUtil;
import com.greenhills.oauth2security.model.business.CompanyEntity;
import com.greenhills.oauth2security.repository.DepartmentRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DepartmentServiceImplTest {
    DepartmentRepository departmentRepository = mock(DepartmentRepository.class);
    DepartmentServiceImpl sut;

    @Before
    public void setUp(){
        sut = new DepartmentServiceImpl();
        sut.departmentRepository = departmentRepository;
    }


    @Test
    public void testGetAllWhenEmptyList() {
        // arrange
        when(departmentRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        // act
        List<Department> companies = sut.getAll();

        // assert
        assertThat(companies).hasSize(0);
    }

    @Test
    public void testGetAllPopulated() {
        // arrange
        Department expectedDepartment = CompanyBuilderUtil.getTestDepartment(1L);
        when(departmentRepository.findAll()).thenReturn(Collections.singletonList(CompanyBuilderUtil.getTestDepartmentEntity(1L)));

        // act
        List<Department> companies = sut.getAll();

        // assert
        assertThat(companies).containsOnly(expectedDepartment);

    }


    @Test
    public void testGetWhenNull() {
        // arrange
        when(departmentRepository.findDepartmentEntitiesById(1L)).thenReturn(null);

        // act
        Department department = sut.get(1L);

        // assert
        assertThat(department).isNull();
    }

    @Test
    public void testGetWhenValueFound() {
        // arrange
        Department expectedDepartment = CompanyBuilderUtil.getTestDepartment(1L);
        when(departmentRepository.findDepartmentEntitiesById(1L)).thenReturn(CompanyBuilderUtil.getTestDepartmentEntity(1L));

        // act
        Department department = sut.get(1L);

        // assert
        assertThat(department).isEqualTo(expectedDepartment);
    }


    @Test
    public void testGetLightweightWhenNull() {
        // arrange
        when(departmentRepository.findDepartmentEntitiesById(1L)).thenReturn(null);

        // act
        LightweightDepartment department = sut.getLightweight(1L);

        // assert
        assertThat(department).isNull();
    }

    @Test
    public void testGetLightweightWhenValueFound() {
        // arrange
        LightweightDepartment expectedDepartment = CompanyBuilderUtil.buildDefaultLightweightDepartment();
        CompanyEntity company = CompanyBuilderUtil.buildDefaultCompanyEntity();
        when(departmentRepository.findDepartmentEntitiesById(1L)).thenReturn(company.getDepartments().iterator().next());

        // act
        LightweightDepartment department = sut.getLightweight(1L);

        // assert
        assertThat(department).isEqualTo(expectedDepartment);
    }
}