package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.event.model.Event;
import com.dhbw.get2gether.backend.event.model.EventDetailDto;
import com.dhbw.get2gether.backend.event.model.EventWidgetUpdateCommand;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.exceptions.OperationNotAllowedException;
import com.dhbw.get2gether.backend.widget.model.Widget;
import org.springframework.security.core.AuthenticatedPrincipal;

import java.util.Objects;
import java.util.Optional;

public abstract class AbstractWidgetService {

    final EventService eventService;

    AbstractWidgetService(EventService eventService) {
        this.eventService = eventService;
    }

    Event addWidget(AuthenticatedPrincipal principal, Event event, Widget widget) {
        boolean containsWidgetType = event.getWidgets().stream()
                .anyMatch(w -> w.getWidgetType() == widget.getWidgetType());
        if (containsWidgetType) {
            throw new OperationNotAllowedException("Event already contains a widget of type " + widget.getWidgetType());
        }
        event.getWidgets().add(0, widget);
        return eventService.updateEventWidgets(
                principal,
                event.getId(),
                new EventWidgetUpdateCommand(event.getWidgets())
        );
    }

    Event updateWidget(AuthenticatedPrincipal principal, Event event, Widget widget) {
        Widget existingWidget = findWidgetFromEvent(event, widget.getId())
                .orElseThrow(() -> new EntityNotFoundException("Widget with id " + widget.getId() + " not found"));
        if (existingWidget.getWidgetType() != widget.getWidgetType()) {
            throw new OperationNotAllowedException("Expected a widget of type " + existingWidget.getWidgetType() + " but found " + widget.getWidgetType());
        }

        int index = event.getWidgets().indexOf(existingWidget);
        if (index == -1) {
            throw new IllegalStateException("Could not find index of existing widget.");
        }
        event.getWidgets().set(index, widget);
        return eventService.updateEventWidgets(
                principal,
                event.getId(),
                new EventWidgetUpdateCommand(event.getWidgets())
        );
    }

    <T extends Widget> T updateAndGetWidget(AuthenticatedPrincipal principal, Event event, T widget) {
        Event updatedEvent = updateWidget(principal, event, widget);
        return getWidgetFromEvent(updatedEvent, widget.getId());
    }

    Event removeWidget(AuthenticatedPrincipal principal, Event event, String widgetId) {
        Widget widget = findWidgetFromEvent(event, widgetId)
                .orElseThrow(() -> new EntityNotFoundException("Widget with id " + widgetId + " not found"));
        if (!event.getWidgets().remove(widget)) {
            throw new IllegalStateException("Failed to remove widget from event.");
        }
        return eventService.updateEventWidgets(
                principal,
                event.getId(),
                new EventWidgetUpdateCommand(event.getWidgets())
        );
    }

    Event getEventById(AuthenticatedPrincipal principal, String eventId) {
        return eventService.getSingleEvent(principal, eventId);
    }

    @SuppressWarnings("unchecked")
    <T extends Widget> Optional<T> findWidgetFromEvent(Event event, String widgetId) {
        return (Optional<T>) event.getWidgets().stream()
                .filter(w -> Objects.equals(w.getId(), widgetId))
                .findFirst();
    }

    <T extends Widget> T getWidgetFromEvent(Event event, String widgetId) {
        return this.<T>findWidgetFromEvent(event, widgetId)
                .orElseThrow(() -> new EntityNotFoundException("Widget with id " + widgetId + " not found"));
    }

    public EventDetailDto mapEventToEventDetailDto(AuthenticatedPrincipal principal, Event event) {
        return eventService.mapEventToEventDetailDto(principal, event);
    }
}
