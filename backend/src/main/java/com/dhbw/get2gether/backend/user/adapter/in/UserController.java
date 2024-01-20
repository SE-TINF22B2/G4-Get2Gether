package com.dhbw.get2gether.backend.user.adapter.in;

import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.User;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String test() {
        return "Hello";
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

    @GetMapping("/user")
    public OAuth2User user(@AuthenticationPrincipal OAuth2User principal) {
        return principal;
    }
    @GetMapping("/user/{id}")
    public User user(@PathVariable String id) {
        return userService.getUserById(id);
    }
}
