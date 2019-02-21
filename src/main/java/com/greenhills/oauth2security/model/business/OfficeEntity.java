package com.greenhills.oauth2security.model.business;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "OFFICE")
@Data
@ToString(exclude = {"department"})
@EqualsAndHashCode(exclude = {"department"})
public class OfficeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
    private Long id = null;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ADDRESS_ID")
    private AddressEntity address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private DepartmentEntity department;
}
