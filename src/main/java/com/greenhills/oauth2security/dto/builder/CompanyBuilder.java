package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Car;
import com.greenhills.oauth2security.dto.Company;
import com.greenhills.oauth2security.dto.Department;
import com.greenhills.oauth2security.model.business.CompanyEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class CompanyBuilder {
    public static Optional<Company> companyFromEntity(CompanyEntity companyEntity) {
        if (companyEntity == null) return Optional.empty();

        Set<Car> cars = Optional.ofNullable(companyEntity.getCars()).orElse(new HashSet<>())
                .stream()
                .map(CarBuilder::carFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        Set<Department> departments = Optional.ofNullable(companyEntity.getDepartments()).orElse(new HashSet<>())
                .stream()
                .map(DepartmentBuilder::departmentFromEntity)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        Company company = Company.builder()
                .id(companyEntity.getId())
                .name(companyEntity.getName())
                .cars(cars)
                .departments(departments)
                .build();

        company.getCars().forEach( car -> car.setCompany(company));
        company.getDepartments().forEach(dept -> dept.setCompany(company));

        return Optional.of(company);

    }

    static Optional<CompanyEntity> entityFromCompany(Company company) {
        if (company == null) return Optional.empty();

        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setName(company.getName());
        return Optional.of(companyEntity);
    }
}
