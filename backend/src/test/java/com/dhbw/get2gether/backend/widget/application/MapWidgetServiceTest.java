package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.AbstractIntegrationTest;
import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventWidgetUpdateCommand;
import com.dhbw.get2gether.backend.exceptions.OperationNotAllowedException;
import com.dhbw.get2gether.backend.utils.WithMockGuestUser;
import com.dhbw.get2gether.backend.utils.WithMockOAuth2User;
import com.dhbw.get2gether.backend.widget.model.map.Location;
import com.dhbw.get2gether.backend.widget.model.map.LocationAddCommand;
import com.dhbw.get2gether.backend.widget.model.map.MapWidget;
import com.dhbw.get2gether.backend.widget.model.map.MapWidgetCreateCommand;
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

class MapWidgetServiceTest extends AbstractIntegrationTest {

    @MockBean
    private EventService eventService;

    @Autowired
    private MapWidgetService mapWidgetService;

    @Test
    @WithMockOAuth2User
    void shouldCreateWidget() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("123")
                .widgets(new ArrayList<>())
                .build();
        MapWidgetCreateCommand createCommand = new MapWidgetCreateCommand();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
                .thenAnswer(i -> event.toBuilder()
                        .widgets(
                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
                        )
                        .build()
                );

        // when
        Event returnedEvent = mapWidgetService.createMapWidget(principal, event.getId(), createCommand);

        // then
        assertThat(returnedEvent).isNotNull();
        assertThat(returnedEvent.getWidgets()).hasSize(1);
        assertThat(returnedEvent.getWidgets().get(0)).isInstanceOf(MapWidget.class).hasNoNullFieldsOrProperties();
    }

    @Test
    @WithMockOAuth2User
    void shouldNotCreateWidgetIfAlreadyExists() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("123")
                .widgets(Collections.singletonList(MapWidget.builder().id("123").build()))
                .build();
        MapWidgetCreateCommand createCommand = new MapWidgetCreateCommand();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);

        // when
        // then
        assertThatThrownBy(() -> mapWidgetService.createMapWidget(principal, event.getId(), createCommand))
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
        MapWidgetCreateCommand createCommand = new MapWidgetCreateCommand();

        // when
        // then
        assertThatThrownBy(() -> mapWidgetService.createMapWidget(principal, event.getId(), createCommand))
                .isInstanceOf(AccessDeniedException.class);
    }


    @Test
    @WithMockOAuth2User
    void shouldAddLocation() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MapWidget widget = MapWidget.builder()
                .id("wi-123")
                .locations(new ArrayList<>())
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();
        LocationAddCommand addCommand = LocationAddCommand.builder()
                .name("Test")
                .placeId("place-123")
                .address("address-123")
                .latitude(12.34)
                .longitude(56.78)
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
        MapWidget returnedWidget = mapWidgetService.addLocation(principal, event.getId(), widget.getId(), addCommand);

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getLocations()).hasSize(1);
        assertThat(returnedWidget.getLocations().get(0)).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(addCommand);
        assertThat(returnedWidget.getLocations().get(0).getId()).isNotBlank();
    }

    @Test
    @WithMockGuestUser
    void shouldNotAddLocationIfUserIsGuest() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MapWidget widget = MapWidget.builder()
                .id("wi-123")
                .locations(new ArrayList<>())
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();
        LocationAddCommand addCommand = LocationAddCommand.builder()
                .name("Test")
                .placeId("place-123")
                .address("address-123")
                .latitude(12.34)
                .longitude(56.78)
                .build();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);

        // when
        // then
        assertThatThrownBy(() -> mapWidgetService.addLocation(principal, event.getId(), widget.getId(), addCommand))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldRemoveLocation() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Location location = Location.builder()
                .id("lo-123")
                .name("Test")
                .placeId("place-123")
                .address("address-123")
                .latitude(12.34)
                .longitude(56.78)
                .build();
        MapWidget widget = MapWidget.builder()
                .id("wi-123")
                .locations(new ArrayList<>(List.of(location)))
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
        MapWidget returnedWidget = mapWidgetService.removeLocation(principal, event.getId(), widget.getId(), location.getId());

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getLocations()).isEmpty();
    }
}