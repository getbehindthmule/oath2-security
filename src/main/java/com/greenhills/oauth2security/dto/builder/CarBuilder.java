package com.greenhills.oauth2security.dto.builder;

import com.greenhills.oauth2security.dto.Car;
import com.greenhills.oauth2security.dto.LightweightCar;
import com.greenhills.oauth2security.model.business.CarEntity;

import java.util.Optional;

@SuppressWarnings("WeakerAccess")
public class CarBuilder {
    static Optional<Car> carFromEntity(CarEntity carEntity) {
        if (carEntity == null) return Optional.empty();

        return Optional.of(
                Car.builder()
                        .id(carEntity.getId())
                        .registrationNumber(carEntity.getRegistrationNumber())
                        .build()
        );
    }

    static Optional<CarEntity> entityFomCar(Car car) {
        if (car == null) return Optional.empty();

        CarEntity carEntity = new CarEntity();
        carEntity.setId(car.getId());
        carEntity.setRegistrationNumber(car.getRegistrationNumber());

        return Optional.of(carEntity);
    }

    static Optional<LightweightCar> lightweightCarFromEntity(CarEntity carEntity) {
        if (carEntity == null) return Optional.empty();

        return Optional.of(
                LightweightCar.builder()
                        .id(carEntity.getId())
                        .registrationNumber(carEntity.getRegistrationNumber())
                        .companyId(carEntity.getCompany().getId())
                        .build()
        );
    }
}
