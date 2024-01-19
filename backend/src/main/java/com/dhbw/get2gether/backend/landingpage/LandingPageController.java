package com.dhbw.get2gether.backend.landingpage;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LandingPageController {
    @GetMapping("/landingpage")
    public String method(@CurrentSecurityContext SecurityContext context, HttpServletResponse httpServletResponse) {
        if(!context.getAuthentication().getPrincipal().toString().equals("anonymousUser")){
            httpServletResponse.setHeader("Location", "http://localhost:4200/dashboard");
            httpServletResponse.setStatus(302);
            return null;
        } else {
            return "Willkommen auf der Landingpage";
        }
    }
}
