package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Company;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Override
    public Company get(Long id) {
        return null;
    }

    @Override
    @PreAuthorize("hasAuthority('COMPANY_READ')")
    public Company get(String name) {
        return Company.builder().id(new Random().nextLong()).name(name).build();
    }

    @Override
    @PreAuthorize("hasAuthority('ALL_COMPANY_READER')")
    public List<Company> getAll() {
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
