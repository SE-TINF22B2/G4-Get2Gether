package com.dhbw.get2gether.backend.widget.adapter.in;

import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.widget.application.ExpenseSplitWidgetService;
import com.dhbw.get2gether.backend.widget.model.expensesplit.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event/{eventId}/widgets/expense-split")
public class ExpenseSplitWidgetController {

    private final ExpenseSplitWidgetService service;

    ExpenseSplitWidgetController(ExpenseSplitWidgetService expenseSplitWidgetService) {
        this.service = expenseSplitWidgetService;
    }

    @PostMapping("/")
    public Event createExpenseSplitWidget(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @RequestBody ExpenseSplitWidgetCreateCommand createCommand
    ) {
        return service.createExpenseSplitWidget(principal, eventId, createCommand);
    }

    @PostMapping("/{widgetId}/entries")
    public ExpenseSplitWidgetDto addEntry(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @RequestBody ExpenseEntryAddCommand addCommand
    ) {
        return service.addEntry(principal, eventId, widgetId, addCommand);
    }

    @DeleteMapping("/{widgetId}/entries/{entryId}")
    public ExpenseSplitWidgetDto removeEntry(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String entryId
    ) {
        return service.removeEntry(principal, eventId, widgetId, entryId);
    }

    @PatchMapping("/{widgetId}/entries/{entryId}")
    public ExpenseSplitWidgetDto updateEntry(
            @AuthenticationPrincipal OAuth2User principal,
            @PathVariable String eventId,
            @PathVariable String widgetId,
            @PathVariable String entryId,
            @RequestBody ExpenseEntryUpdateCommand addCommand
    ) {
        return service.updateEntry(principal, eventId, widgetId, entryId, addCommand);
    }

}
