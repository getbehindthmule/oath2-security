package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Address;
import com.greenhills.oauth2security.dto.Office;
import com.greenhills.oauth2security.model.business.AddressEntity;
import com.greenhills.oauth2security.model.business.DepartmentEntity;
import com.greenhills.oauth2security.model.business.OfficeEntity;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class OfficeBuilderTest {
    final Long id = 2L;
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
}