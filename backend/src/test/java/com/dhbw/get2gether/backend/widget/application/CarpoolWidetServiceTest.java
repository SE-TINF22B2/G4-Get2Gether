package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.AbstractIntegrationTest;
import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventParticipantDto;
import com.dhbw.get2gether.backend.event.model.EventWidgetUpdateCommand;
import com.dhbw.get2gether.backend.exceptions.OperationNotAllowedException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.utils.WithMockGuestUser;
import com.dhbw.get2gether.backend.utils.WithMockOAuth2User;
import com.dhbw.get2gether.backend.widget.model.carpool.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class CarpoolWidetServiceTest extends AbstractIntegrationTest {
    @MockBean
    private EventService eventService;
    @MockBean
    private UserService userService;
    @Autowired
    private CarpoolWidgetService carpoolWidgetService;

    @Test
    @WithMockOAuth2User
    void shouldCreateWidget(){
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("123")
                .widgets(new ArrayList<>())
                .build();
        CarpoolCreateCommand createCommand = new CarpoolCreateCommand();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
                .thenAnswer(i -> event.toBuilder()
                        .widgets(
                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
                        )
                        .build()
                );
        String eventId = event.getId();
        // when
        Event returnedEvent = carpoolWidgetService.createCarpoolWidget(principal, event.getId(), createCommand);

        // then
        assertThat(returnedEvent).isNotNull();
        assertThat(returnedEvent.getWidgets()).hasSize(1);
        assertThat(returnedEvent.getWidgets().get(0)).isInstanceOf(CarpoolWidget.class).hasNoNullFieldsOrProperties();
    }
    @Test
    @WithMockOAuth2User
    void shouldNotCreateWidgetIfAlreadyExists() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("123")
                .widgets(Collections.singletonList(CarpoolWidget.builder().id("123").build()))
                .build();
        CarpoolCreateCommand createCommand = new CarpoolCreateCommand();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);

        // when
        // then
        assertThatThrownBy(() -> carpoolWidgetService.createCarpoolWidget(principal, event.getId(), createCommand))
                .isInstanceOf(OperationNotAllowedException.class);
    }
    @Test
    @WithMockGuestUser
    void shouldNotCreateWidgetIfUserIsGuest() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("123")
                .widgets(new ArrayList<>())
                .build();
        CarpoolCreateCommand createCommand = new CarpoolCreateCommand();

        // when
        // then
        assertThatThrownBy(() -> carpoolWidgetService.createCarpoolWidget(principal, event.getId(), createCommand))
                .isInstanceOf(AccessDeniedException.class);
    }
    @Test
    @WithMockOAuth2User
    void shouldAddCar() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CarpoolWidget widget = CarpoolWidget.builder()
                .id("wi-123")
                .cars(new ArrayList<>())
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();
        CarAddCommand addCommand = CarAddCommand.builder()
                .driverAdress("some Address")
                .anzahlPlaetze(3)
                .build();
        EventParticipantDto driver = EventParticipantDto.builder()
                .id("test")
                .firstName("driver")
                .lastName("driver")
                .profilePictureUrl("profilePictureUrl")
                .build();
        CarDto car = CarDto.builder()
                .driverAdress("some Address")
                .anzahlPlaetze(3)
                .driver(driver)
                .build();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
                .thenAnswer(i -> event.toBuilder()
                        .widgets(
                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
                        )
                        .build()
                );
        when(userService.getUserByPrincipal(principal)).thenReturn(User.builder()
                .id("test")
                .email("test@example.com").build());
        when(eventService.getAllEventParticipants(any())).thenReturn(List.of(driver));

        // when
        CarpoolWidgetDto returnedWidget = carpoolWidgetService.addCar(principal, event.getId(), widget.getId(), addCommand);

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getCars()).hasSize(1);
        assertThat(returnedWidget.getCars().get(0)).usingRecursiveComparison()
                .ignoringFields("id", "riders")
                .isEqualTo(car);
        assertThat(returnedWidget.getCars().get(0).getId()).isNotBlank();
    }
    @Test
    @WithMockGuestUser
    void shouldNotAddCarIfUserIsGuest() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CarpoolWidget widget = CarpoolWidget.builder()
                .id("wi-123")
                .cars(new ArrayList<>())
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();
        CarAddCommand addCommand = CarAddCommand.builder()
                .driverAdress("some Address")
                .anzahlPlaetze(3)
                .build();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);

        // when
        // then
        assertThatThrownBy(() -> carpoolWidgetService.addCar(principal, event.getId(), widget.getId(), addCommand))
                .isInstanceOf(AccessDeniedException.class);
    }
    @Test
    @WithMockOAuth2User
    void shouldRemoveCar() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Car car = Car.builder()
                .driverId("test")
                .build();
        CarpoolWidget widget = CarpoolWidget.builder()
                .id("wi-123")
                .cars(new ArrayList<>(List.of(car)))
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
                .thenAnswer(i -> event.toBuilder()
                        .widgets(
                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
                        )
                        .build()
                );

        // when
        CarpoolWidgetDto returnedWidget = carpoolWidgetService.removeCar(principal, event.getId(), widget.getId(), car.getId());

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getCars()).isEmpty();
    }
    @Test
    @WithMockOAuth2User
    void shouldAddRider() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Car car = Car.builder()
                .driverId("test")
                .build();
        CarpoolWidget widget = CarpoolWidget.builder()
                .id("wi-123")
                .cars(new ArrayList<>(List.of(car)))
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();
        RiderAddCommand addCommand = RiderAddCommand.builder()
                .pickupPlace("Testpickup")
                .build();
        RiderDto rider = RiderDto.builder()
                .pickupPlace("Testpickup")
                .build();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
                .thenAnswer(i -> event.toBuilder()
                        .widgets(
                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
                        )
                        .build()
                );
        when(userService.getUserByPrincipal(principal)).thenReturn(User.builder()
                .id("test")
                .email("test@example.com").build());

        // when
        CarpoolWidgetDto returnedWidget = carpoolWidgetService.addRider(principal, event.getId(), widget.getId(), car.getId(), addCommand);

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getCars().get(0).getRiders()).hasSize(1);
        assertThat(returnedWidget.getCars().get(0).getRiders().get(0)).usingRecursiveComparison()
                .ignoringFields("id", "user")
                .isEqualTo(rider);
        assertThat(returnedWidget.getCars().get(0).getRiders().get(0).getPickupPlace()).isNotNull();
    }
    @Test
    @WithMockGuestUser
    void shouldNotAddRiderIfUserIsGuest() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Car car = Car.builder()
                .driverId("test")
                .build();
        CarpoolWidget widget = CarpoolWidget.builder()
                .id("wi-123")
                .cars(new ArrayList<>(List.of(car)))
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();
        RiderAddCommand addCommand = RiderAddCommand.builder()
                .pickupPlace("Testpickup")
                .build();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);

        // when
        // then
        assertThatThrownBy(() -> carpoolWidgetService.addRider(principal, event.getId(), widget.getId(), car.getId(), addCommand))
                .isInstanceOf(AccessDeniedException.class);
    }
    @Test
    @WithMockOAuth2User
    void shouldRemoveRider() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Rider rider = Rider.builder()
                .id("test")
                .pickupPlace("Testpickup")
                .userId("test")
                .build();
        Car car = Car.builder()
                .driverId("test")
                .riders(new ArrayList<>(List.of(rider)))
                .build();
        CarpoolWidget widget = CarpoolWidget.builder()
                .id("wi-123")
                .cars(new ArrayList<>(List.of(car)))
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();


        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
                .thenAnswer(i -> event.toBuilder()
                        .widgets(
                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
                        )
                        .build()
                );

        // when
        CarpoolWidgetDto returnedWidget = carpoolWidgetService.removeRider(principal, event.getId(), widget.getId(), car.getId(), rider.getId());

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getCars().get(0).getRiders()).isEmpty();
    }
}
