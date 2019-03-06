package com.greenhills.oauth2security.repository;

import com.greenhills.oauth2security.model.business.CompanyEntity;
import org.springframework.data.repository.CrudRepository;

public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {
    CompanyEntity findByName(String name);
    CompanyEntity findCompanyEntityById(Long id);
}
