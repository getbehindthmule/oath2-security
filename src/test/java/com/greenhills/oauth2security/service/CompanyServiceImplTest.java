package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.dto.LightweightCompany;
import com.greenhills.oauth2security.dto.builder.CompanyBuilderUtil;
import com.greenhills.oauth2security.model.business.CompanyEntity;
import com.greenhills.oauth2security.repository.CompanyRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings({"unchecked", "OptionalGetWithoutIsPresent", "WeakerAccess"})
public class CompanyServiceImplTest {
    private final CompanyRepository companyRepository = mock(CompanyRepository.class);
    private CompanyServiceImpl sut;

    @Before
    public void setUp() {
        sut = new CompanyServiceImpl();
        sut.companyRepository = companyRepository;
    }

    @Test
    public void testGetAllWhenEmptyList() {
        // arrange
        when(companyRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        // act
        List<Company> companies = sut.getAll();

        // assert
        assertThat(companies).hasSize(0);
    }

    @Test
    public void testGetAllPopulated() {
        // arrange
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        when(companyRepository.findAll()).thenReturn(Collections.singletonList(CompanyBuilderUtil.buildDefaultCompanyEntity()));

        // act
        List<Company> companies = sut.getAll();

        // assert
        assertThat(companies).containsOnly(expectedCompany);

    }

    @Test
    public void testGetWhenNull() {
        // arrange
        when(companyRepository.findByName(anyString())).thenReturn(null);

        // act
        Company company = sut.get("company");

        // assert
        assertThat(company).isNull();
    }

    @Test
    public void testGetLightweightWhenNull() {
        // arrange
        when(companyRepository.findByName(anyString())).thenReturn(null);

        // act
        Company company = sut.get("company");

        // assert
        assertThat(company).isNull();
    }

    @Test
    public void testGetWhenValueFound() {
        // arrange
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        when(companyRepository.findByName(anyString())).thenReturn(CompanyBuilderUtil.buildDefaultCompanyEntity());

        // act
        Company company = sut.get("company");

        // assert
        assertThat(company).isEqualTo(expectedCompany);
    }

    @Test
    public void testGetLightweightWhenValueFound() {
        // arrange
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();
        when(companyRepository.findByName(anyString())).thenReturn(CompanyBuilderUtil.buildDefaultCompanyEntity());

        // act
        LightweightCompany company = sut.getLightweight("company");

        // assert
        assertThat(company).isEqualTo(expectedCompany);
    }

    @Test
    public void testGetLightweightByIdWhenValueFound() {
        // arrange
        LightweightCompany expectedCompany = CompanyBuilderUtil.buildDefaultLightweightCompany();
        when(companyRepository.findCompanyEntityById(1L)).thenReturn(CompanyBuilderUtil.buildDefaultCompanyEntity());

        // act
        LightweightCompany company = sut.getLightweight(1L);

        // assert
        assertThat(company).isEqualTo(expectedCompany);
    }

    @Test
    public void testCreateWhenCannotCreateEntity() {
        // arrange

        // act
        Optional<Long> company = sut.create(null);

        // assert
        assertThat(company.isPresent()).isFalse();
    }


    @Test
    public void testCreateWhenSuccessful() {
        // arrange
        when(companyRepository.save(any())).thenReturn(CompanyBuilderUtil.buildDefaultCompanyEntity());
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        Company inputCompany = CompanyBuilderUtil.buildDefaultCompany();
        inputCompany.setId(null);

        // act
        Optional<Long> actualCompany = sut.create(inputCompany);

        // assert
        assertThat(actualCompany.isPresent()).isTrue();
        assertThat(actualCompany.get()).isEqualTo(expectedCompany.getId());
    }


    @Test
    public void testUpdateWhenCannotCreateEntity() {
        // arrange

        // act
        Long company = sut.update(null);

        // assert
        assertThat(company).isNull();
    }

    @Test
    public void testUpdateWhenRepositoryReturnsNull() {
        // arrange
        when(companyRepository.save(any())).thenReturn(null);
        // act
        Long company = sut.update(CompanyBuilderUtil.buildDefaultCompany());

        // assert
        assertThat(company).isNull();
    }

    @Test
    public void testUpdateWhenSuccessful() {
        // arrange
        when(companyRepository.save(any())).thenReturn(CompanyBuilderUtil.buildDefaultCompanyEntity());
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        Company inputCompany = CompanyBuilderUtil.buildDefaultCompany();
        inputCompany.setId(null);

        // act
        Long actualCompany = sut.update(inputCompany);

        // assert
        assertThat(actualCompany).isEqualTo(expectedCompany.getId());
    }

    @Test
    public void testUpdateWhenCompanyPrimaryKeyIsDefined() {
        // arrange
        Long updatedId = 2L;
        Company inputCompany = CompanyBuilderUtil.buildDefaultCompany();
        inputCompany.setId(updatedId);
        CompanyEntity companyEntity = CompanyBuilderUtil.buildDefaultCompanyEntity();
        companyEntity.setId(updatedId);
        when(companyRepository.save(any())).thenReturn(companyEntity);

        // act
        Long company = sut.update(CompanyBuilderUtil.buildDefaultCompany());

        // assert
        assertThat(company).isEqualTo(updatedId);
    }

    @Test
    public void testGetIdValueFound() {
        // arrange
        Company expectedCompany = CompanyBuilderUtil.buildDefaultCompany();
        when(companyRepository.findCompanyEntityById(1L)).thenReturn(CompanyBuilderUtil.buildDefaultCompanyEntity());

        // act
        Company company = sut.get(1L);

        // assert
        assertThat(company).isEqualTo(expectedCompany);
    }

    @Test
    public void testGetIdValueNotFound() {
        // arrange
        when(companyRepository.findCompanyEntityById(1L)).thenReturn(null);

        // act
        Company company = sut.get(1L);

        // assert
        assertThat(company).isNull();
    }

}