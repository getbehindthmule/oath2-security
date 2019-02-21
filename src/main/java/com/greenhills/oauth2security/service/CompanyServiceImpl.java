package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.dto.builder.CompanyBuilder;
import com.greenhills.oauth2security.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Override
    public Company get(Long id) {
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('COMPANY_READ')")
    @Transactional
    public Company get(String name) {
        return CompanyBuilder.companyFromEntity(companyRepository.findByName(name)).orElse(null);
    }

    @Override
    @PreAuthorize("hasAuthority('ALL_COMPANY_READER')")
    public List<Company> getAll() {
        List<Company> companies = StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(CompanyBuilder::companyFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());

        return Arrays.asList(Company.builder().id(1L).name("green hills").build());
    }

    @Override
    @PreAuthorize("hasAuthority('COMPANY_CREATE')")
    public void create(Company company) {

    }

    @Override
    public Company update(Company company) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(Company company) {

    }
}
