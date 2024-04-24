package com.dhbw.get2gether.backend.helper;

import com.dhbw.get2gether.backend.event.application.EventService;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class InvitationController {
    private final EventService eventService;

    InvitationController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/event/invitation/{invitationLink}")
    public String getInvitationLink(
            @AuthenticationPrincipal AuthenticatedPrincipal principal,
            HttpServletResponse httpServletResponse,
            @PathVariable String invitationLink) {
        Optional<String> locationUrl = eventService.openEventFromInvitationLink(principal, invitationLink);
        if (locationUrl.isPresent()) {
            httpServletResponse.setHeader("Location", locationUrl.get());
            httpServletResponse.setStatus(HttpServletResponse.SC_FOUND);
            return null;
        } else {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return "invalid_invitation";
        }
    }
}
