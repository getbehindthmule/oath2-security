package com.greenhills.oauth2security.model.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@javax.persistence.Entity
@Table(name = "DEPARTMENT")
@Data
@ToString(exclude = "company")
@EqualsAndHashCode(exclude = "company")
@FilterDef(name = "authorizeDepartment", parameters = {@ParamDef(name = "userId", type = "long")})
@Filter(name = "authorizeDepartment", condition = " ID in " +
        "(" +
            "select d.ID from USER_ u, DEPARTMENT d join APP_USERS_COMPANIES uc on uc.USER_ID = u.ID and d.COMPANY_ID = uc.COMPANY_ID where u.ID = :userId" +
        ")")
public class DepartmentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id = null;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Set<EmployeeEntity> employees = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "department", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Set<OfficeEntity> offices = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private CompanyEntity company;

}
