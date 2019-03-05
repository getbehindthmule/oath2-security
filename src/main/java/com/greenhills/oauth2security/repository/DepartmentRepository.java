package com.greenhills.oauth2security.repository;

import com.greenhills.oauth2security.model.business.DepartmentEntity;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<DepartmentEntity, Long> {
    DepartmentEntity findDepartmentEntitiesById(Long id);
}
