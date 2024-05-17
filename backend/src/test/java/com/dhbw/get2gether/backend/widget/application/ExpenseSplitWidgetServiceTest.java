package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.AbstractIntegrationTest;
import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventWidgetUpdateCommand;
import com.dhbw.get2gether.backend.exceptions.OperationNotAllowedException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.utils.WithMockGuestUser;
import com.dhbw.get2gether.backend.utils.WithMockOAuth2User;
import com.dhbw.get2gether.backend.widget.model.expensesplit.*;
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

class ExpenseSplitWidgetServiceTest extends AbstractIntegrationTest {

    @MockBean
    private EventService eventService;

    @MockBean
    private UserService userService;

    @Autowired
    private ExpenseSplitWidgetService expenseSplitWidgetService;

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
        ExpenseSplitWidgetCreateCommand createCommand = new ExpenseSplitWidgetCreateCommand();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
                .thenAnswer(i -> event.toBuilder()
                        .widgets(
                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
                        )
                        .build()
                );

        // when
        Event returnedEvent = expenseSplitWidgetService.createExpenseSplitWidget(principal, event.getId(), createCommand);

        // then
        assertThat(returnedEvent).isNotNull();
        assertThat(returnedEvent.getWidgets()).hasSize(1);
        assertThat(returnedEvent.getWidgets().get(0)).isInstanceOf(ExpenseSplitWidget.class).hasNoNullFieldsOrProperties();
    }

    @Test
    @WithMockOAuth2User
    void shouldNotCreateWidgetIfAlreadyExists() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event event = Event.builder()
                .id("123")
                .widgets(Collections.singletonList(ExpenseSplitWidget.builder().id("123").build()))
                .build();
        ExpenseSplitWidgetCreateCommand createCommand = new ExpenseSplitWidgetCreateCommand();

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);

        // when
        // then
        assertThatThrownBy(() -> expenseSplitWidgetService.createExpenseSplitWidget(principal, event.getId(), createCommand))
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
        ExpenseSplitWidgetCreateCommand createCommand = new ExpenseSplitWidgetCreateCommand();

        // when
        // then
        assertThatThrownBy(() -> expenseSplitWidgetService.createExpenseSplitWidget(principal, event.getId(), createCommand))
                .isInstanceOf(AccessDeniedException.class);
    }


    @Test
    @WithMockOAuth2User
    void shouldAddEntry() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ExpenseSplitWidget widget = ExpenseSplitWidget.builder()
                .id("wi-123")
                .entries(new ArrayList<>())
                .build();
        Event event = Event.builder()
                .id("ev-123")
                .widgets(new ArrayList<>(List.of(widget)))
                .build();
        ExpenseEntryAddCommand addCommand = ExpenseEntryAddCommand.builder()
                .description("Test")
                .price(10)
                .involvedUsers(List.of("test"))
                .build();

        SimpleUserDto simpleUser = SimpleUserDto.builder()
                .id("test")
                .firstName("firstName")
                .lastName("lastName")
                .profilePictureUrl("profilePictureUrl")
                .build();
        UserWithPercentageDto user = UserWithPercentageDto.builder()
                .user(simpleUser)
                .percentage(1)
                .build();
        ExpenseEntryDto entry = ExpenseEntryDto.builder()
                .description("Test")
                .price(10)
                .creatorId("test")
                .involvedUsers(List.of(user))
                .pricePerPerson(10)
                .percentagePerPerson(1)
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
        when(userService.getSimpleUsersById(any())).thenReturn(List.of(simpleUser));
        when(userService.getUserById("test")).thenReturn(User.builder()
                .id("test")
                .firstName("firstName")
                .lastName("lastName")
                .profilePictureUrl("profilePictureUrl")
                .email("test@example.com").build());

        // when
        ExpenseSplitWidgetDto returnedWidget = expenseSplitWidgetService.addEntry(principal, event.getId(), widget.getId(), addCommand);

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getEntries()).hasSize(1);
        assertThat(returnedWidget.getEntries().get(0)).usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(entry);
        assertThat(returnedWidget.getEntries().get(0).getId()).isNotBlank();
    }

//    @Test
//    @WithMockGuestUser
//    void shouldNotAddEntryIfUserIsGuest() {
//        // given
//        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
//                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        ShoppingListWidget widget = ShoppingListWidget.builder()
//                .id("wi-123")
//                .entries(new ArrayList<>())
//                .build();
//        Event event = Event.builder()
//                .id("ev-123")
//                .widgets(new ArrayList<>(List.of(widget)))
//                .build();
//        EntryAddCommand addCommand = EntryAddCommand.builder()
//                .description("Test")
//                .amount("1")
//                .build();
//
//        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
//
//        // when
//        // then
//        assertThatThrownBy(() -> shoppingListWidgetService.addEntry(principal, event.getId(), widget.getId(), addCommand))
//                .isInstanceOf(AccessDeniedException.class);
//    }
//
//    @Test
//    @WithMockOAuth2User
//    void shouldRemoveEntry() {
//        // given
//        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
//                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Entry entry = Entry.builder()
//                .description("Test")
//                .amount("1")
//                .checked(false)
//                .creatorId("creator-id")
//                .id("123")
//                .build();
//        ShoppingListWidget widget = ShoppingListWidget.builder()
//                .id("wi-123")
//                .entries(new ArrayList<>(List.of(entry)))
//                .build();
//        Event event = Event.builder()
//                .id("ev-123")
//                .widgets(new ArrayList<>(List.of(widget)))
//                .build();
//
//        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
//        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
//                .thenAnswer(i -> event.toBuilder()
//                        .widgets(
//                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
//                        )
//                        .build()
//                );
//
//        // when
//        ShoppingListWidget returnedWidget = shoppingListWidgetService.removeEntry(principal, event.getId(), widget.getId(), entry.getId());
//
//        // then
//        assertThat(returnedWidget).isNotNull();
//        assertThat(returnedWidget.getEntries()).isEmpty();
//    }
//
//    @Test
//    @WithMockOAuth2User
//    void shouldCheckEntry() {
//        // given
//        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
//                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Entry entry = Entry.builder()
//                .description("Test")
//                .amount("1")
//                .checked(false)
//                .creatorId("creator-id")
//                .id("123")
//                .build();
//        ShoppingListWidget widget = ShoppingListWidget.builder()
//                .id("wi-123")
//                .entries(new ArrayList<>(List.of(entry)))
//                .build();
//        Event event = Event.builder()
//                .id("ev-123")
//                .widgets(new ArrayList<>(List.of(widget)))
//                .build();
//        EntryCheckCommand checkCommand = EntryCheckCommand.builder()
//                .isChecked(true)
//                .build();
//        Entry entryAfterCheck = Entry.builder()
//                .description("Test")
//                .amount("1")
//                .checked(true)
//                .creatorId("creator-id")
//                .buyerId("test")
//                .id("123")
//                .build();
//
//        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
//        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
//                .thenAnswer(i -> event.toBuilder()
//                        .widgets(
//                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
//                        )
//                        .build()
//                );
//        when(userService.getUserByPrincipal(principal)).thenReturn(User.builder()
//                .id("test")
//                .email("test@example.com").build());
//
//        // when
//        ShoppingListWidget returnedWidget = shoppingListWidgetService.checkEntry(principal, event.getId(), widget.getId(), entry.getId(), checkCommand);
//
//        // then
//        assertThat(returnedWidget).isNotNull();
//        assertThat(returnedWidget.getEntries()).hasSize(1);
//        assertThat(returnedWidget.getEntries().get(0)).usingRecursiveComparison()
//                .ignoringFields("id")
//                .isEqualTo(entryAfterCheck);
//        assertThat(returnedWidget.getEntries().get(0).getId()).isNotBlank();
//    }
//
//    @Test
//    @WithMockOAuth2User
//    void shouldUnCheckEntry() {
//        // given
//        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
//                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        Entry entry = Entry.builder()
//                .description("Test")
//                .amount("1")
//                .checked(true)
//                .creatorId("creator-id")
//                .buyerId("test")
//                .id("123")
//                .build();
//        ShoppingListWidget widget = ShoppingListWidget.builder()
//                .id("wi-123")
//                .entries(new ArrayList<>(List.of(entry)))
//                .build();
//        Event event = Event.builder()
//                .id("ev-123")
//                .widgets(new ArrayList<>(List.of(widget)))
//                .build();
//        EntryCheckCommand checkCommand = EntryCheckCommand.builder()
//                .isChecked(false)
//                .build();
//        Entry entryAfterCheck = Entry.builder()
//                .description("Test")
//                .amount("1")
//                .checked(false)
//                .creatorId("creator-id")
//                .buyerId("test")
//                .id("123")
//                .build();
//
//        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);
//        when(eventService.updateEventWidgets(any(), eq(event.getId()), any()))
//                .thenAnswer(i -> event.toBuilder()
//                        .widgets(
//                                i.getArgument(2, EventWidgetUpdateCommand.class).getWidgets()
//                        )
//                        .build()
//                );
//        when(userService.getUserByPrincipal(principal)).thenReturn(User.builder()
//                .id("test")
//                .email("test@example.com").build());
//
//        // when
//        ShoppingListWidget returnedWidget = shoppingListWidgetService.checkEntry(principal, event.getId(), widget.getId(), entry.getId(), checkCommand);
//
//        // then
//        assertThat(returnedWidget).isNotNull();
//        assertThat(returnedWidget.getEntries()).hasSize(1);
//        assertThat(returnedWidget.getEntries().get(0)).usingRecursiveComparison()
//                .ignoringFields("id")
//                .isEqualTo(entryAfterCheck);
//        assertThat(returnedWidget.getEntries().get(0).getId()).isNotBlank();
//    }
}