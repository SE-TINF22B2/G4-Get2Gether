package com.dhbw.get2gether.backend.event.application;

import com.dhbw.get2gether.backend.AbstractIntegrationTest;
import com.dhbw.get2gether.backend.authentication.GuestAuthenticationPrincipal;
import com.dhbw.get2gether.backend.event.adapter.out.EventRepository;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventCreateCommand;
import com.dhbw.get2gether.backend.event.model.EventOverviewDto;
import com.dhbw.get2gether.backend.event.model.EventUpdateCommand;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.utils.WithMockGuestUser;
import com.dhbw.get2gether.backend.utils.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
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

        when(userService.getUserByPrincipal(any())).thenThrow(EntityNotFoundException.class);
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

        when(userService.getUserByPrincipal(any())).thenReturn(user);
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
    void shouldGetEventsFromUser() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder().id("test").build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findEventsByParticipantIdsContainsOrderByDateDesc(eq(user.getId())))
                .thenReturn(Collections.singletonList(event));

        // when
        List<EventOverviewDto> events = eventService.getAllEventsFromUser(principal);

        // then
        verify(eventRepository).findEventsByParticipantIdsContainsOrderByDateDesc(eq(user.getId()));
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
    void shouldGetSingleEventIfUserIsParticipant() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("test")
                .participantIds(Collections.singletonList(user.getId()))
                .build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        Event returnedEvent = eventService.getSingleEvent(principal, event.getId());

        // then
        assertThat(returnedEvent).isEqualTo(event);
    }

    @Test
    @WithMockOAuth2User
    void shouldNotGetSingleEventIfUserIsNotParticipant() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("test")
                .participantIds(Collections.singletonList("foo"))
                .build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        // then
        assertThatThrownBy(() -> eventService.getSingleEvent(principal, event.getId()))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockGuestUser
    void shouldGetSingleEventIfUserIsGuest() {
        // given
        GuestAuthenticationPrincipal principal = (GuestAuthenticationPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("test")
                .participantIds(Collections.singletonList("foo"))
                .build();
        principal.grantAccessToEvent(event.getId());

        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        Event returnedEvent = eventService.getSingleEvent(principal, event.getId());

        // then
        assertThat(returnedEvent).isEqualTo(event);
    }

    @Test
    @WithMockGuestUser
    void shouldNotGetSingleEventIfUserIsGuestAndNotGrantedAccessToEvent() {
        // given
        GuestAuthenticationPrincipal principal = (GuestAuthenticationPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("test")
                .participantIds(Collections.singletonList("foo"))
                .build();

        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        // then
        assertThatThrownBy(() -> eventService.getSingleEvent(principal, event.getId()))
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
                .isInstanceOf(AccessDeniedException.class);
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

    @Test
    @WithMockOAuth2User
    void shouldNotUpdateEventIfUserIsNotParticipant() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("1")
                .participantIds(Collections.singletonList("foo"))
                .build();
        EventUpdateCommand command = EventUpdateCommand.builder().build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        // then
        assertThatThrownBy(() -> eventService.updateEvent(principal, event.getId(), command))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldUpdateEventIfUserIsParticipant() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("1")
                .participantIds(Collections.singletonList("test"))
                .name("old")
                .build();
        EventUpdateCommand command = EventUpdateCommand.builder().name("new").build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));
        when(eventRepository.save(any())).thenAnswer(args -> args.getArgument(0));

        // when
        Event updatedEvent = eventService.updateEvent(principal, event.getId(), command);

        // then
        assertThat(updatedEvent).isNotNull();
        assertThat(updatedEvent.getName()).isEqualTo(command.getName());
    }

    @Test
    @WithMockOAuth2User
    void shouldNotGenerateInvitationLinkIfUserIsNotParticipant() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("1")
                .participantIds(Collections.singletonList("foo"))
                .build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));

        // when
        // then
        assertThatThrownBy(() -> eventService.generateInvitationLink(principal, event.getId()))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldGenerateInvitationLinkIfUserIsParticipant() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("1")
                .participantIds(Collections.singletonList("test"))
                .build();

        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));
        when(eventRepository.save(any())).thenAnswer(args -> args.getArgument(0));

        // when
        Event updatedEvent = eventService.generateInvitationLink(principal, event.getId());

        // then
        assertThat(updatedEvent).isNotNull();
        assertThat(updatedEvent.getInvitationLink()).isNotBlank();
    }

    @Test
    @WithMockGuestUser
    void shouldOpenEventFromInvitationLinkIfUserIsGuest() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String invitationLink = "123";
        Event event = Event.builder().id("1").invitationLink(invitationLink).build();

        when(eventRepository.findByInvitationLink(eq(invitationLink))).thenReturn(Optional.of(event));

        // when
        Optional<String> route = eventService.openEventFromInvitationLink(principal, invitationLink);

        // then
        assertThat(route).isPresent().asString().isNotBlank();
        assertThat(((GuestAuthenticationPrincipal) principal).getGrantedEventIds())
                .containsExactly(event.getId());
    }

    @Test
    @WithMockOAuth2User
    void shouldOpenEventFromInvitationLinkIfIsUser() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        String invitationLink = "123";
        Event event = Event.builder()
                .id("1")
                .invitationLink(invitationLink)
                .participantIds(new ArrayList<>())
                .build();

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        when(userService.getUserByPrincipal(any())).thenReturn(user);
        when(eventRepository.findByInvitationLink(eq(invitationLink))).thenReturn(Optional.of(event));
        when(eventRepository.save(eventCaptor.capture())).thenAnswer(args -> args.getArgument(0));

        // when
        Optional<String> route = eventService.openEventFromInvitationLink(principal, invitationLink);

        // then
        assertThat(route).isPresent().asString().isNotBlank();
        assertThat(eventCaptor.getValue().getParticipantIds()).contains(user.getId());
    }

    @Test
    @WithMockOAuth2User
    void shouldLeaveEventIfIsUser() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = User.builder().id("test").build();
        Event event = Event.builder()
                .id("1")
                .participantIds(new ArrayList<>(List.of(user.getId())))
                .build();

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        when(userService.getUserByPrincipal(any())).thenReturn(user);
        when(userService.findUserFromPrincipal(any())).thenReturn(Optional.of(user));
        when(eventRepository.findById(eq(event.getId()))).thenReturn(Optional.of(event));
        when(eventRepository.save(eventCaptor.capture())).thenAnswer(args -> args.getArgument(0));

        // when
        boolean isLeaved = eventService.leaveEvent(principal, event.getId());

        // then
        assertThat(isLeaved).isTrue();
        assertThat(eventCaptor.getValue().getParticipantIds()).doesNotContain(user.getId());
    }
}
