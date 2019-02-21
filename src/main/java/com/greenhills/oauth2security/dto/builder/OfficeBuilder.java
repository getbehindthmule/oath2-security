package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Office;
import com.greenhills.oauth2security.model.business.OfficeEntity;

import java.util.Optional;

public class OfficeBuilder {
    static Optional<Office> officeFromEntity(OfficeEntity officeEntity){
        if (officeEntity == null) return Optional.empty();

        return Optional.of(
                Office.builder()
                        .id(officeEntity.getId())
                        .name(officeEntity.getName())
                        .address(AddressBuilder.addressFromEntity(officeEntity.getAddress()).orElse(null))
                        .build()
        );

    }
}
