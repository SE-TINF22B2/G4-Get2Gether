package com.dhbw.get2gether.backend.widget.adapter.in;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.widget.application.ShoppingListWidgetService;
import com.dhbw.get2gether.backend.widget.model.shoppinglist.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/{eventId}/widgets/shopping-list")
public class ShoppingListWidgetController {

    private final ShoppingListWidgetService service;

    ShoppingListWidgetController(ShoppingListWidgetService shoppingListWidgetService) {
        this.service = shoppingListWidgetService;
    }

    @PostMapping("/")
    public Event createShoppingListWidget(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @RequestBody ShoppingListCreateCommand createCommand
    ) {
        return service.createShoppingListWidget(principal, eventId, createCommand);
    }

    @PostMapping("/{widgetId}/entries")
    public ShoppingListWidget addEntry(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @RequestBody EntryAddCommand addCommand
    ) {
        return service.addEntry(principal, eventId, widgetId, addCommand);
    }

    @DeleteMapping("/{widgetId}/entries/{entryId}")
    public ShoppingListWidget removeEntry(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String entryId
    ) {
        return service.removeEntry(principal, eventId, widgetId, entryId);
    }

    @PutMapping("/{widgetId}/entries/{entryId}")
    public ShoppingListWidget checkEntry(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String entryId,
            @RequestBody EntryCheckCommand checkCommand
            ) {
        return service.checkEntry(principal, eventId, widgetId, entryId, checkCommand);
    }

    @PutMapping("/{widgetId}/entries/update/{entryId}")
    public ShoppingListWidget updateEntry(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String entryId,
            @RequestBody EntryUpdateCommand updateCommand
    ) {
        return service.updateEntry(principal, eventId, widgetId, entryId, updateCommand);
    }

}
