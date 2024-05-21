package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.SimpleUserDto;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.widget.application.mapper.ExpenseSplitMapper;
import com.dhbw.get2gether.backend.widget.application.mapper.WidgetMapper;
import com.dhbw.get2gether.backend.widget.model.expensesplit.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ExpenseSplitWidgetService extends AbstractWidgetService {

    private final ExpenseSplitMapper mapper;
    private final WidgetMapper widgetMapper;
    private final UserService userService;

    ExpenseSplitWidgetService(EventService eventService, WidgetMapper widgetMapper, ExpenseSplitMapper mapper, UserService userService) {
        super(eventService);
        this.mapper = mapper;
        this.widgetMapper = widgetMapper;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    public Event createExpenseSplitWidget(AuthenticatedPrincipal principal, String eventId, ExpenseSplitWidgetCreateCommand createCommand) {
        Event event = getEventById(principal, eventId);
        ExpenseSplitWidget widget = mapper.mapToExpenseSplitWidget(createCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .build();
        return addWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public ExpenseSplitWidgetDto addEntry(AuthenticatedPrincipal principal, String eventId, String widgetId, ExpenseEntryAddCommand addCommand) {
        if(addCommand.getInvolvedUsers().isEmpty()) {
            throw new IllegalArgumentException("At least one user must be involved in the expense entry");
        }

        Event event = getEventById(principal, eventId);
        ExpenseSplitWidget widget = getWidgetFromEvent(event, widgetId);

        ExpenseEntry entry = mapper.mapToEntry(addCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .creatorId(userService.getUserByPrincipal(principal).getId())
                .involvedUsers(addCommand.getInvolvedUsers().stream().map(user ->
                        UserWithPercentage.builder()
                                .userId(user)
                                .percentage((double) 1 /addCommand.getInvolvedUsers().size())
                                .build()).toList())
                .build();
        widget.addEntry(entry);
        return mapToDto(updateAndGetWidget(principal, event, widget), event.getParticipantIds(), principal);
    }

    @PreAuthorize("hasRole('USER')")
    public ExpenseSplitWidgetDto removeEntry(AuthenticatedPrincipal principal, String eventId, String widgetId, String entryId) {
        Event event = getEventById(principal, eventId);
        ExpenseSplitWidget widget = getWidgetFromEvent(event, widgetId);
        ExpenseEntry entry = widget.getEntries().stream()
                .filter(l -> Objects.equals(l.getId(), entryId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Entry not found"));
        if (!widget.removeEntry(entry)) {
            throw new IllegalStateException("Failed to remove entry from shopping list widget");
        }
        return mapToDto(updateAndGetWidget(principal, event, widget), event.getParticipantIds(), principal);
    }

    @PreAuthorize("hasRole('USER')")
    public ExpenseSplitWidgetDto updateEntry(AuthenticatedPrincipal principal, String eventId, String widgetId, String entryId, ExpenseEntryUpdateCommand updateCommand) {
        if(updateCommand.getInvolvedUsers().isEmpty()) {
            throw new IllegalArgumentException("At least one user must be involved in the expense entry");
        }

        Event event = getEventById(principal, eventId);
        ExpenseSplitWidget widget = getWidgetFromEvent(event, widgetId);
        ExpenseEntry original_entry = widget.getEntries().stream()
                .filter(l -> Objects.equals(l.getId(), entryId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Entry not found"));

        ExpenseEntry updatedEntry = mapper.mapToEntry(updateCommand).toBuilder()
                .id(original_entry.getId())
                .creatorId(original_entry.getCreatorId())
                .involvedUsers(updateCommand.getInvolvedUsers().stream().map(user ->
                        UserWithPercentage.builder()
                                .userId(user)
                                .percentage((double) 1 /updateCommand.getInvolvedUsers().size())
                                .build()).toList())
                .build();

        widget.replaceEntry(original_entry, updatedEntry);
        return mapToDto(updateAndGetWidget(principal, event, widget), event.getParticipantIds(), principal);
    }

    private ExpenseSplitWidgetDto mapToDto(ExpenseSplitWidget widget, List<String> participantIds, AuthenticatedPrincipal principal) {
        List<SimpleUserDto> simpleUserDtos = userService.getSimpleUsersById(participantIds);
        User user = userService.getUserByPrincipal(principal);
        List<Debt> debts = widget.calculateDebtsForUserId(user.getId());
        return widgetMapper.expenseSplitWidgetToExpenseSplitWidgetDto(widget, debts, simpleUserDtos);
    }
}
