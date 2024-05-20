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
import org.junit.jupiter.api.Nested;
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


    @Test
    @WithMockGuestUser
    void shouldNotAddEntryIfUserIsGuest() {
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

        when(eventService.getSingleEvent(any(), eq(event.getId()))).thenReturn(event);

        // when
        // then
        assertThatThrownBy(() -> expenseSplitWidgetService.addEntry(principal, event.getId(), widget.getId(), addCommand))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldRemoveEntry() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ExpenseEntry entry = ExpenseEntry.builder()
                .description("Test")
                .price(10)
                .creatorId("creator-id")
                .id("123")
                .involvedUsers(List.of(UserWithPercentage.builder().userId("test").percentage(1).build()))
                .build();

        ExpenseSplitWidget widget = ExpenseSplitWidget.builder()
                .id("wi-123")
                .entries(new ArrayList<>(List.of(entry)))
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
        ExpenseSplitWidgetDto returnedWidget = expenseSplitWidgetService.removeEntry(principal, event.getId(), widget.getId(), entry.getId());

        // then
        assertThat(returnedWidget).isNotNull();
        assertThat(returnedWidget.getEntries()).isEmpty();
    }

    @Nested
    class Calculation{
        @Test
        @WithMockOAuth2User
        void shouldCalculateDeptsAsOnlyBuyer(){
            // given
            UserWithPercentage mainUser = UserWithPercentage.builder()
                    .userId("test")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user2 = UserWithPercentage.builder()
                    .userId("user2")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user3 = UserWithPercentage.builder()
                    .userId("user3")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user4 = UserWithPercentage.builder()
                    .userId("user4")
                    .percentage(0.25)
                    .build();
            ExpenseEntry entry1 = ExpenseEntry.builder()
                    .id("1")
                    .price(10)
                    .description("Bier")
                    .creatorId("test")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();
            ExpenseEntry entry2 = ExpenseEntry.builder()
                    .id("2")
                    .price(20)
                    .description("Bier2")
                    .creatorId("test")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();

            ExpenseSplitWidget widget = ExpenseSplitWidget.builder()
                    .id("wi-123")
                    .entries(List.of(entry1, entry2))
                    .build();

            // when
            List<Dept> depts = widget.calculateDeptsForUserId(mainUser.getUserId());

            // then
            assertThat(depts.get(0).getUserId()).isEqualTo(user2.getUserId());
            assertThat(depts.get(0).getDeptAmount()).isEqualTo(user2.getPercentage()*entry1.getPrice()+user2.getPercentage()*entry2.getPrice());
            assertThat(depts.get(1).getUserId()).isEqualTo(user3.getUserId());
            assertThat(depts.get(1).getDeptAmount()).isEqualTo(user3.getPercentage()*entry1.getPrice()+user3.getPercentage()*entry2.getPrice());
            assertThat(depts.get(2).getUserId()).isEqualTo(user4.getUserId());
            assertThat(depts.get(2).getDeptAmount()).isEqualTo(user4.getPercentage()*entry1.getPrice()+user4.getPercentage()*entry2.getPrice());
            assertThat(depts).hasSize(3);
        }

        @Test
        @WithMockOAuth2User
        void shouldCalculateDeptsAsOnlyReceiver(){
            // given
            UserWithPercentage mainUser = UserWithPercentage.builder()
                    .userId("test")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user2 = UserWithPercentage.builder()
                    .userId("user2")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user3 = UserWithPercentage.builder()
                    .userId("user3")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user4 = UserWithPercentage.builder()
                    .userId("user4")
                    .percentage(0.25)
                    .build();
            ExpenseEntry entry1 = ExpenseEntry.builder()
                    .id("1")
                    .price(10)
                    .description("Bier")
                    .creatorId("user2")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();
            ExpenseEntry entry2 = ExpenseEntry.builder()
                    .id("2")
                    .price(20)
                    .description("Bier2")
                    .creatorId("user3")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();

            ExpenseSplitWidget widget = ExpenseSplitWidget.builder()
                    .id("wi-123")
                    .entries(List.of(entry1, entry2))
                    .build();

            // when
            List<Dept> depts = widget.calculateDeptsForUserId(mainUser.getUserId());

            // then
            assertThat(depts.get(0).getUserId()).isEqualTo(user2.getUserId());
            assertThat(depts.get(0).getDeptAmount()).isEqualTo(mainUser.getPercentage()*entry1.getPrice()*-1);
            assertThat(depts.get(1).getUserId()).isEqualTo(user3.getUserId());
            assertThat(depts.get(1).getDeptAmount()).isEqualTo(mainUser.getPercentage()*entry2.getPrice()*-1);
            assertThat(depts).hasSize(2);
        }

        @Test
        @WithMockOAuth2User
        void shouldCalculateDeptsAsOnlyReceiverFromOneOtherUser(){
            // given
            UserWithPercentage mainUser = UserWithPercentage.builder()
                    .userId("test")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user2 = UserWithPercentage.builder()
                    .userId("user2")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user3 = UserWithPercentage.builder()
                    .userId("user3")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user4 = UserWithPercentage.builder()
                    .userId("user4")
                    .percentage(0.25)
                    .build();
            ExpenseEntry entry1 = ExpenseEntry.builder()
                    .id("1")
                    .price(10)
                    .description("Bier")
                    .creatorId("user2")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();
            ExpenseEntry entry2 = ExpenseEntry.builder()
                    .id("2")
                    .price(20)
                    .description("Bier2")
                    .creatorId("user2")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();

            ExpenseSplitWidget widget = ExpenseSplitWidget.builder()
                    .id("wi-123")
                    .entries(List.of(entry1, entry2))
                    .build();

            // when
            List<Dept> depts = widget.calculateDeptsForUserId(mainUser.getUserId());

            // then
            assertThat(depts.get(0).getUserId()).isEqualTo(user2.getUserId());
            assertThat(depts.get(0).getDeptAmount()).isEqualTo(mainUser.getPercentage()*entry1.getPrice()*-1+mainUser.getPercentage()*entry2.getPrice()*-1);
            assertThat(depts).hasSize(1);
        }

        @Test
        @WithMockOAuth2User
        void shouldCalculateDeptsAsBuyerAndReceiver(){
            // given
            UserWithPercentage mainUser = UserWithPercentage.builder()
                    .userId("test")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user2 = UserWithPercentage.builder()
                    .userId("user2")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user3 = UserWithPercentage.builder()
                    .userId("user3")
                    .percentage(0.25)
                    .build();
            UserWithPercentage user4 = UserWithPercentage.builder()
                    .userId("user4")
                    .percentage(0.25)
                    .build();
            ExpenseEntry entry1 = ExpenseEntry.builder()
                    .id("1")
                    .price(10)
                    .description("Bier")
                    .creatorId("test")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();
            ExpenseEntry entry2 = ExpenseEntry.builder()
                    .id("2")
                    .price(20)
                    .description("Bier2")
                    .creatorId("user2")
                    .involvedUsers(List.of(mainUser, user2, user3, user4))
                    .build();

            ExpenseSplitWidget widget = ExpenseSplitWidget.builder()
                    .id("wi-123")
                    .entries(List.of(entry1, entry2))
                    .build();

            // when
            List<Dept> depts = widget.calculateDeptsForUserId(mainUser.getUserId());

            // then
            assertThat(depts.get(0).getUserId()).isEqualTo(user2.getUserId());
            assertThat(depts.get(0).getDeptAmount()).isEqualTo(user2.getPercentage()*entry1.getPrice()+mainUser.getPercentage()*entry2.getPrice()*-1);
            assertThat(depts.get(1).getUserId()).isEqualTo(user3.getUserId());
            assertThat(depts.get(1).getDeptAmount()).isEqualTo(user3.getPercentage()*entry1.getPrice());
            assertThat(depts.get(2).getUserId()).isEqualTo(user4.getUserId());
            assertThat(depts.get(2).getDeptAmount()).isEqualTo(user4.getPercentage()*entry1.getPrice());
            assertThat(depts).hasSize(3);
        }


    }
}