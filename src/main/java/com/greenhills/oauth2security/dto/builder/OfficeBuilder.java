package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.LightweightOffice;
import com.greenhills.oauth2security.dto.Office;
import com.greenhills.oauth2security.model.business.OfficeEntity;

import java.util.Optional;

@SuppressWarnings("WeakerAccess")
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

    static Optional<LightweightOffice> lightweightOfficeFromEntity(OfficeEntity officeEntity){
        if (officeEntity == null) return Optional.empty();

        return Optional.of(
                LightweightOffice.builder()
                        .id(officeEntity.getId())
                        .name(officeEntity.getName())
                        .address(AddressBuilder.addressFromEntity(officeEntity.getAddress()).orElse(null))
                        .departmentId(officeEntity.getDepartment().getId())
                        .build()
        );
    }

    static Optional<OfficeEntity> entityFromOffice( Office office) {
        if (office == null) return Optional.empty();

        OfficeEntity officeEntity = new OfficeEntity();
        officeEntity.setId(office.getId());
        officeEntity.setName(office.getName());
        officeEntity.setAddress(AddressBuilder.entityFromAddress(office.getAddress()).orElse(null));
        return Optional.of(officeEntity);
    }
}
