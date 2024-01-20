package com.dhbw.get2gether.backend.user.adapter.in;

import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.Token;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(@Autowired UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String test() {
        return "Hello";
    }

    @GetMapping("/any")
    public ResponseEntity<Token> any() {
        return new ResponseEntity<>(new Token("Works"), HttpStatus.OK);
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

    @GetMapping("/user")
    public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
        return Collections.singletonMap("username", principal.getAttribute("name"));
    }
    @GetMapping("/user/{id}")
    public User user(@PathVariable String id) {
        return userService.getUserById(id);
    }
}
