package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Address;
import com.greenhills.oauth2security.model.business.AddressEntity;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "WeakerAccess"})
public class AddressBuilderTest {

    @Test
    public void testWhenMappingNull() {
        // arrange

        // act
        Optional<Address> actualAddress = AddressBuilder.addressFromEntity(null);

        // assert
        assertThat(actualAddress.isPresent()).isFalse();
    }

    @Test
    public void testWhenToEntityMappingNull() {
        // arrange

        // act
        Optional<AddressEntity> actualAddress = AddressBuilder.entityFromAddress(null);

        // assert
        assertThat(actualAddress.isPresent()).isFalse();
    }

    @Test
    public void testWhenMappingIsPresent() {
        // arrange
        final Long id = 2L;
        final String street = "Taits Lane";
        final String houseNumber = "35";
        final String zipCode = "DD6 9BW";

        final AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(id);
        addressEntity.setStreet(street);
        addressEntity.setHouseNumber(houseNumber);
        addressEntity.setZipCode(zipCode);

        final Address expectedAddress = Address.builder()
                .id(id)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();

        // act
        Optional<Address> actualAddress = AddressBuilder.addressFromEntity(addressEntity);

        // assert
        assertThat(actualAddress.get()).isEqualTo(expectedAddress);
    }

    @Test
    public void testWhenToEntityMappingIsPresent() {
        // arrange
        final Long id = 2L;
        final String street = "Taits Lane";
        final String houseNumber = "35";
        final String zipCode = "DD6 9BW";

        final AddressEntity expectedAddress = new AddressEntity();
        expectedAddress.setId(id);
        expectedAddress.setStreet(street);
        expectedAddress.setHouseNumber(houseNumber);
        expectedAddress.setZipCode(zipCode);

        final Address address = Address.builder()
                .id(id)
                .street(street)
                .houseNumber(houseNumber)
                .zipCode(zipCode)
                .build();

        // act
        Optional<AddressEntity> actualAddress = AddressBuilder.entityFromAddress(address);

        // assert
        assertThat(actualAddress.get()).isEqualTo(expectedAddress);
    }
}