package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Address;
import com.greenhills.oauth2security.model.business.AddressEntity;

import java.util.Optional;

public class AddressBuilder {
    static Optional<Address> addressFromEntity(AddressEntity addressEntity) {
        if (addressEntity == null) return Optional.empty();

        return Optional.of(
                Address.builder()
                        .id(addressEntity.getId())
                        .street(addressEntity.getStreet())
                        .houseNumber(addressEntity.getHouseNumber())
                        .zipCode(addressEntity.getZipCode())
                        .build()
        );
    }

    static Optional<AddressEntity> entityFromAddress(Address address) {
        if (address == null) return Optional.empty();

        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setId(address.getId());
        addressEntity.setStreet(address.getStreet());
        addressEntity.setHouseNumber(address.getHouseNumber());
        addressEntity.setZipCode(address.getZipCode());
        return Optional.of(addressEntity);
    }
}
