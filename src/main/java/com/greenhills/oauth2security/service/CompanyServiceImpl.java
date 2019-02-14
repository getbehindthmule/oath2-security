package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Company;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Override
    public Company get(Long id) {
        return null;
    }

    @Override
    public Company get(String name) {
        return null;
    }

    @Override
    public List<Company> getAll() {
        return Arrays.asList(Company.builder().id(1L).name("green hills").build());
    }

    @Override
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
