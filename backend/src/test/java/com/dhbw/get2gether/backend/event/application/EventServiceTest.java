package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.AbstractIntegrationTest;
import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.exceptions.AuthorizationException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.utils.WithMockGuestUser;
import com.dhbw.get2gether.backend.utils.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EventServiceTest extends AbstractIntegrationTest {

    @MockBean
    private EventRepository eventRepository;

    @MockBean
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Test
    @WithMockGuestUser
    void shouldNotCreateEventsIfUnauthenticated() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventCreateCommand command = EventCreateCommand.builder().build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.empty());
        when(eventRepository.insert((Event) any())).thenAnswer(args -> args.getArgument(0));

        // when
        // then
        assertThatThrownBy(() -> eventService.createEvent(principal, command))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldCreateEventsIfAuthenticated() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EventCreateCommand command = EventCreateCommand.builder().name("Test").build();
        User user = User.builder().id("test").build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.insert((Event) any())).thenAnswer(args -> args.getArgument(0));

        // when
        Event event = eventService.createEvent(principal, command);

        // then
        verify(eventRepository).insert((Event) any());
        assertThat(event).isNotNull();
        assertThat(event.getName()).isEqualTo(command.getName());
    }

    @Test
    @WithMockOAuth2User
    void shouldNotGetAllEventsIfNotAdmin() {
        // given
        when(eventRepository.findAll()).thenReturn(Collections.emptyList());

        // when
        // then
        assertThatThrownBy(() -> eventService.getAllEvents()).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User(authorities = {"ROLE_ADMIN"})
    void shouldGetAllEventsIfAdmin() {
        // given
        Event event = Event.builder().build();
        when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));

        // when
        List<Event> events = eventService.getAllEvents();

        // then
        verify(eventRepository).findAll();
        assertThat(events).hasSize(1);
    }

    @Test
    @WithMockOAuth2User
    void shouldGetEventsFromUser() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder().id("test").build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findEventsByParticipantIdsContains(eq(user.getId())))
                .thenReturn(Collections.singletonList(event));

        // when
        List<Event> events = eventService.getAllEventsFromUser(principal);

        // then
        verify(eventRepository).findEventsByParticipantIdsContains(eq(user.getId()));
        assertThat(events).hasSize(1);
    }

    @Test
    @WithMockGuestUser
    void shouldNotGetEventsFromGuest() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.empty());

        // when
        // then
        assertThatThrownBy(() -> eventService.getAllEventsFromUser(principal))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldNotDeleteEventIfUserIsNotCreator() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("1")
                .participantIds(Collections.singletonList(user.getId()))
                .creatorId("foo")
                .build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        // then
        assertThatThrownBy(() -> eventService.deleteEventById(principal, event.getId()))
                .isInstanceOf(AuthorizationException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldDeleteEventIfUserIsCreator() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("1")
                .participantIds(Collections.singletonList(user.getId()))
                .creatorId(user.getId())
                .build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        // then
        assertThatCode(() -> eventService.deleteEventById(principal, event.getId()))
                .doesNotThrowAnyException();
    }
}
