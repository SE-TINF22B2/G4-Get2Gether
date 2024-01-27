package com.dhbw.get2gether.backend.helper;

import com.dhbw.get2gether.backend.event.application.EventService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvitationController {
    private final EventService eventService;

    InvitationController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event/invitation/{invitationLink}")
    public void getInvitationLink(
            @AuthenticationPrincipal AuthenticatedPrincipal principal,
            HttpServletResponse httpServletResponse,
            @PathVariable String invitationLink) {
        httpServletResponse.setHeader("Location", eventService.getRouteFromInvitationLink(principal, invitationLink));
        httpServletResponse.setStatus(302);
    }
}
