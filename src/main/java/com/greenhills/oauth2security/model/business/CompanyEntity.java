package com.greenhills.oauth2security.model.business;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "COMPANY", uniqueConstraints = {@UniqueConstraint(columnNames = {"NAME"})})
@Data
@EqualsAndHashCode(of = "id")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"user", "createdAt", "updatedAt"},
        allowGetters = true)
@FilterDef(name = "authorize", parameters = {@ParamDef(name = "userId", type = "long")})
@Filter(name = "authorize", condition = " ID in (select uc.COMPANY_ID from USER_ u join APP_USERS_COMPANIES uc on uc.User_Id=u.id where u.id=:userId)")
public class CompanyEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id = null;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Set<DepartmentEntity> departments = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "company", fetch = FetchType.LAZY, orphanRemoval = true)
    @JsonManagedReference
    private Set<CarEntity> cars = new HashSet<>();

    @CreatedBy
    @Column(name = "CREATED_BY", nullable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "MODIFIED_BY", nullable = false)
    private String modifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    @CreatedDate
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED_AT", nullable = false)
    @LastModifiedDate
    private Date updatedAt;

}

