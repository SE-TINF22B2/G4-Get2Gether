package com.dhbw.get2gether.backend.user.adapter.in;

import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.user.model.UpdateUserCommand;
import com.dhbw.get2gether.backend.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/error")
    public String error(HttpServletRequest request) {
        String message = (String) request.getSession().getAttribute("error.message");
        request.getSession().removeAttribute("error.message");
        return message;
    }

    @GetMapping("/user")
    public User user(@AuthenticationPrincipal AuthenticatedPrincipal principal) {
        return userService.getUserByPrincipal(principal);
    }

    @GetMapping("/user/{id}")
    public User user(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("user/self")
    public User updateUser(@AuthenticationPrincipal AuthenticatedPrincipal principal, @RequestBody UpdateUserCommand updateUserCommand){
        return userService.updateUser(principal, updateUserCommand);
    }
}
