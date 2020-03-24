package com.greenhills.oauth2security.dto.builder;


import com.greenhills.oauth2security.dto.Car;
import com.greenhills.oauth2security.dto.LightweightCar;
import com.greenhills.oauth2security.model.business.CarEntity;
import com.greenhills.oauth2security.model.business.CompanyEntity;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings({"OptionalGetWithoutIsPresent", "WeakerAccess"})
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
    public void testLightweightWhenMappingNull() {
        // arrange

        // act
        Optional<LightweightCar> actualCar = CarBuilder.lightweightCarFromEntity(null);

        // assert
        assertThat(actualCar.isPresent()).isFalse();
    }

    @Test
    public void testWhenEntityMappingNull() {
        // arrange

        // act
        Optional<CarEntity> actualCar = CarBuilder.entityFomCar(null);

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

    @Test
    public void testLightweightWhenMappingIsPresent() {
        // arrange
        final Long id = 2L;
        final String reg = "YC 12 EOT";
        CompanyEntity companyEntity = new CompanyEntity();
        companyEntity.setId(1L);
        CarEntity carEntity = new CarEntity();
        carEntity.setId(2L);
        carEntity.setRegistrationNumber(reg);
        carEntity.setCompany(companyEntity);
        LightweightCar expectedCar = LightweightCar.builder()
                .id(id)
                .registrationNumber(reg)
                .companyId(1L)
                .build();

        // act
        Optional<LightweightCar> actualCar = CarBuilder.lightweightCarFromEntity(carEntity);

        // assert
        assertThat(actualCar.get()).isEqualTo(expectedCar);
    }
    @Test
    public void testWhenEntityMappingIsPresent() {
        // arrange
        final Long id = 2L;
        final String reg = "YC 12 EOT";
        CarEntity expectedCar = new CarEntity();
        expectedCar.setId(2L);
        expectedCar.setRegistrationNumber(reg);
        Car car = Car.builder()
                .id(id)
                .registrationNumber(reg)
                .build();

        // act
        Optional<CarEntity> actualCar = CarBuilder.entityFomCar(car);

        // assert
        assertThat(actualCar.get()).isEqualTo(expectedCar);
    }

}