package com.dhbw.get2gether.backend.widget.model.carpool;

import com.dhbw.get2gether.backend.exceptions.OperationNotAllowedException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CarTest {

    @Test
    void shouldAddRider() {
        // given
        Car car = Car.builder()
                .anzahlPlaetze(1)
                .build();
        Rider rider = Rider.builder()
                .userId("test")
                .build();

        // when
        car.addRider(rider);

        // then
        assertThat(car.getRiders()).containsExactlyInAnyOrder(rider);
    }

    @Test
    void shouldNotAddRiderIfAlreadyAdded() {
        // given
        Rider rider = Rider.builder()
                .userId("test")
                .build();
        Car car = Car.builder()
                .anzahlPlaetze(2)
                .riders(new ArrayList<>(List.of(rider)))
                .build();

        // when
        // then
        assertThatThrownBy(() -> car.addRider(rider)).isInstanceOf(OperationNotAllowedException.class);
    }

    @Test
    void shouldNotAddRiderIfCarIsFull() {
        // given
        Rider rider = Rider.builder()
                .userId("test")
                .build();
        Rider otherRider = Rider.builder()
                .userId("other")
                .build();
        Car car = Car.builder()
                .anzahlPlaetze(1)
                .riders(new ArrayList<>(List.of(otherRider)))
                .build();

        // when
        // then
        assertThatThrownBy(() -> car.addRider(rider)).isInstanceOf(OperationNotAllowedException.class);
    }

    @Test
    void shouldNotAddRiderIfRiderIsDriver() {
        // given
        Rider rider = Rider.builder()
                .userId("test")
                .build();
        Car car = Car.builder()
                .anzahlPlaetze(1)
                .driverId(rider.getUserId())
                .build();

        // when
        // then
        assertThatThrownBy(() -> car.addRider(rider)).isInstanceOf(OperationNotAllowedException.class);
    }

    @Test
    void shouldRemoveRider() {
        // given
        Rider rider = Rider.builder()
                .userId("test")
                .build();
        Car car = Car.builder()
                .anzahlPlaetze(2)
                .riders(new ArrayList<>(List.of(rider)))
                .build();

        // when
        car.removeRider(rider);

        // then
        assertThat(car.getRiders()).doesNotContain(rider);
    }

    @Test
    void shouldNotRemoveRiderIfNotExist() {
        // given
        Rider rider = Rider.builder()
                .userId("test")
                .build();
        Car car = Car.builder()
                .anzahlPlaetze(1)
                .build();

        // when
        // then
        assertThatThrownBy(() -> car.removeRider(rider)).isInstanceOf(OperationNotAllowedException.class);
    }
}