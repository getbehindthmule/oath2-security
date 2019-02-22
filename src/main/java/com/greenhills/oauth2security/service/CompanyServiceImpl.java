package com.greenhills.oauth2security.service;

import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.dto.builder.CompanyBuilder;
import com.greenhills.oauth2security.model.business.CompanyEntity;
import com.greenhills.oauth2security.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
        return StreamSupport.stream(companyRepository.findAll().spliterator(), false)
                .map(CompanyBuilder::companyFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
    }

    @Override
    @PreAuthorize("hasAuthority('COMPANY_CREATE')")
    @Transactional
    public Long create(Company company) {
        Optional<CompanyEntity> companyEntity = CompanyBuilder.entityFromCompany(company);
        if (!companyEntity.isPresent() || (companyEntity.get().getId() != null)) return null;

        CompanyEntity savedCompany = companyRepository.save(companyEntity.get());

        return Optional.ofNullable(savedCompany).map(CompanyEntity::getId).orElse(null);
    }

    @Override
    public Long update(Company company) {
        Optional<CompanyEntity> companyEntity = CompanyBuilder.entityFromCompany(company);
        if (!companyEntity.isPresent() ) return null;

        CompanyEntity savedCompany = companyRepository.save(companyEntity.get());

        return Optional.ofNullable(savedCompany).map(CompanyEntity::getId).orElse(null);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public void delete(Company company) {

    }
}
