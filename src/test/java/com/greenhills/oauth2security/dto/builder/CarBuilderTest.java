package com.greenhills.oauth2security.dto.builder;


import com.greenhills.oauth2security.dto.Car;
import com.greenhills.oauth2security.model.business.CarEntity;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class CarBuilderTest {

    @Test
    public void testWhenMappingNull() {
        // arrange

        // act
        Optional<Car> actualCar = CarBuilder.carFromEntity(null);

        // assert
        assertThat(actualCar.isPresent()).isFalse();
    }

    @Test
    public void testWhenMappingIsPresent() {
        // arrange
        final Long id = 2L;
        final String reg = "YC 12 EOT";
        CarEntity carEntity = new CarEntity();
        carEntity.setId(2L);
        carEntity.setRegistrationNumber(reg);
        Car expectedCar = Car.builder()
                .id(id)
                .registrationNumber(reg)
                .build();

        // act
        Optional<Car> actualCar = CarBuilder.carFromEntity(carEntity);

        // assert
        assertThat(actualCar.get()).isEqualTo(expectedCar);
    }

}