package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.widget.application.mapper.ShoppingListMapper;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.Entry;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.EntryAddCommand;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.ShoppingListCreateCommand;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.ShoppingListWidget;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ShoppingListWidgetService extends AbstractWidgetService {

    private final ShoppingListMapper mapper;
    private final UserService userService;

    ShoppingListWidgetService(EventService eventService, ShoppingListMapper mapper, UserService userService) {
        super(eventService);
        this.mapper = mapper;
        this.userService = userService;
    }

    @PreAuthorize("hasRole('USER')")
    public Event createShoppingListWidget(AuthenticatedPrincipal principal, String eventId, ShoppingListCreateCommand createCommand) {
        Event event = getEventById(principal, eventId);
        ShoppingListWidget widget = mapper.mapToShoppingListWidget(createCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .entries(List.of())
                .build();
        return addWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public ShoppingListWidget addEntry(AuthenticatedPrincipal principal, String eventId, String widgetId, EntryAddCommand addCommand) {
        Event event = getEventById(principal, eventId);
        ShoppingListWidget widget = getWidgetFromEvent(event, widgetId);

        Entry entry = mapper.mapToEntry(addCommand).toBuilder()
                .id(UUID.randomUUID().toString())
                .checked(false)
                .creatorId(userService.getUserByPrincipal(principal).getId())
                .build();

        widget.addEntry(entry);
        return updateAndGetWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public ShoppingListWidget removeEntry(AuthenticatedPrincipal principal, String eventId, String widgetId, String entryId) {
        Event event = getEventById(principal, eventId);
        ShoppingListWidget widget = getWidgetFromEvent(event, widgetId);
        Entry entry = widget.getEntries().stream()
                .filter(l -> Objects.equals(l.getId(), entryId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Entry not found"));
        if (!widget.removeEntry(entry)) {
            throw new IllegalStateException("Failed to remove entry from event.");
        }
        return updateAndGetWidget(principal, event, widget);
    }

    @PreAuthorize("hasRole('USER')")
    public ShoppingListWidget checkEntry(AuthenticatedPrincipal principal, String eventId, String widgetId, String entryId) {
        Event event = getEventById(principal, eventId);
        ShoppingListWidget widget = getWidgetFromEvent(event, widgetId);
        Entry entry = widget.getEntries().stream()
                .filter(l -> Objects.equals(l.getId(), entryId)).findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Entry not found"));
        entry.check(userService.getUserByPrincipal(principal).getId());
        return updateAndGetWidget(principal, event, widget);
    }

}
