package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Address;
import com.greenhills.oauth2security.dto.LightweightOffice;
import com.greenhills.oauth2security.dto.Office;
import com.greenhills.oauth2security.model.business.AddressEntity;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.model.business.OfficeEntity;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OfficeBuilderTest {
    final Long id = 2L;
    final Long deptId = 3L;
    final String street = "Taits Lane";
    final String houseNumber = "35";
    final String zipCode = "DD6 9BW";
    final String name = "planning";

    private AddressEntity getTestAddressEntity() {
        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet(street);
        addressEntity.setHouseNumber(houseNumber);
        addressEntity.setZipCode(zipCode);

        return addressEntity;
    }

    private Address getTestAddress() {
        return Address.builder()
                .id(id)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();
    }

    @Test
    public void testWhenMappingNull() {
        // arrange

        // act
        Optional<Office> actualOffice = OfficeBuilder.officeFromEntity(null);

        // assert
        assertThat(actualOffice.isPresent()).isFalse();
    }

    @Test
    public void testLightweightWhenMappingNull() {
        // arrange

        // act
        Optional<LightweightOffice> actualOffice = OfficeBuilder.lightweightOfficeFromEntity(null);

        // assert
        assertThat(actualOffice.isPresent()).isFalse();
    }

    @Test
    public void testWhenEntityMappingNull() {
        // arrange

        // act
        Optional<OfficeEntity> actualOffice = OfficeBuilder.entityFromOffice(null);

        // assert
        assertThat(actualOffice.isPresent()).isFalse();
    }

    @Test
    public void testWhenMappingIsPresent() {
        // arrange
        OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(id);
        officeEntity.setName(name);
        officeEntity.setDepartment(new DepartmentEntity());
        officeEntity.setAddress(getTestAddressEntity());

        Office expectedOffice = Office.builder()
                .id(id)
                .name(name)
                .department(null)
                .address(getTestAddress())
                .build();

        // act
        Optional<Office> actualOffice = OfficeBuilder.officeFromEntity(officeEntity);

        // assert
        assertThat(actualOffice.get()).isEqualTo(expectedOffice);
    }

    @Test
    public void testLightweightWhenMappingIsPresent() {
        // arrange
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(deptId);
        OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(id);
        officeEntity.setName(name);
        officeEntity.setDepartment(new DepartmentEntity());
        officeEntity.setAddress(getTestAddressEntity());
        officeEntity.setDepartment(departmentEntity);

        LightweightOffice expectedOffice = LightweightOffice.builder()
                .id(id)
                .name(name)
                .departmentId(deptId)
                .address(getTestAddress())
                .build();

        // act
        Optional<LightweightOffice> actualOffice = OfficeBuilder.lightweightOfficeFromEntity(officeEntity);

        // assert
        assertThat(actualOffice.get()).isEqualTo(expectedOffice);
    }

    @Test
    public void testWhenEntityMappingIsPresent() {
        // arrange
        OfficeEntity expectedOffice = new OfficeEntity();
        expectedOffice.setId(id);
        expectedOffice.setName(name);
        expectedOffice.setDepartment(null);
        expectedOffice.setAddress(getTestAddressEntity());

        Office office = Office.builder()
                .id(id)
                .name(name)
                .department(null)
                .address(getTestAddress())
                .build();

        // act
        Optional<OfficeEntity> actualOffice = OfficeBuilder.entityFromOffice(office);

        // assert
        assertThat(actualOffice.get()).isEqualTo(expectedOffice);
    }

    @Test
    public void testWhenAddressIsMissing() {
        // arrange
        OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(id);
        officeEntity.setName(name);
        officeEntity.setDepartment(null);
        officeEntity.setAddress(null);

        Office expectedOffice = Office.builder()
                .id(id)
                .name(name)
                .department(null)
                .address(null)
                .build();

        // act
        Optional<Office> actualOffice = OfficeBuilder.officeFromEntity(officeEntity);

        // assert
        assertThat(actualOffice.get()).isEqualTo(expectedOffice);
    }

    @Test
    public void testLightweightWhenAddressIsMissing() {
        // arrange
        DepartmentEntity departmentEntity = new DepartmentEntity();
        departmentEntity.setId(deptId);
        OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(id);
        officeEntity.setName(name);
        officeEntity.setDepartment(departmentEntity);
        officeEntity.setAddress(null);

        LightweightOffice expectedOffice = LightweightOffice.builder()
                .id(id)
                .name(name)
                .departmentId(deptId)
                .address(null)
                .build();

        // act
        Optional<LightweightOffice> actualOffice = OfficeBuilder.lightweightOfficeFromEntity(officeEntity);

        // assert
        assertThat(actualOffice.get()).isEqualTo(expectedOffice);
    }

    @Test
    public void testWhenEntityAddressIsMissing() {
        // arrange
        OfficeEntity expectedOffice = new OfficeEntity();
        expectedOffice.setId(id);
        expectedOffice.setName(name);
        expectedOffice.setDepartment(null);
        expectedOffice.setAddress(null);

        Office office = Office.builder()
                .id(id)
                .name(name)
                .department(null)
                .address(null)
                .build();

        // act
        Optional<OfficeEntity> actualOffice = OfficeBuilder.entityFromOffice(office);

        // assert
        assertThat(actualOffice.get()).isEqualTo(expectedOffice);
    }
}