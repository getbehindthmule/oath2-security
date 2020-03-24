package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.dto.LightweightCompany;

import java.util.List;
import java.util.Optional;

@SuppressWarnings({"EmptyMethod", "unused"})
public interface CompanyService {
    Company get(Long id);

    Company get(String name);

    LightweightCompany getLightweight(String name);

    LightweightCompany getLightweight(Long id);

    List<Company> getAll();

    Optional<Long> create(Company company);

    Long update(Company company);

    void delete(Long id);

    void delete(Company company);
}
