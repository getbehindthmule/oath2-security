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
}
