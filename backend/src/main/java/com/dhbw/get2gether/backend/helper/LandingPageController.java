package com.dhbw.get2gether.backend.helper;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LandingPageController {

    private final Environment env;

    LandingPageController(Environment env) {
        this.env = env;
    }

    @GetMapping("/")
    public String method(@CurrentSecurityContext SecurityContext context, HttpServletResponse httpServletResponse) {
        if (!context.getAuthentication().getPrincipal().toString().equals("anonymousUser")) {
            httpServletResponse.setHeader("Location", this.env.getProperty("frontend.url") + "/dashboard");
            httpServletResponse.setStatus(HttpServletResponse.SC_FOUND);
            return null;
        } else {
            return "index";
        }
    }
}
