package com.dhbw.backend.user.adapter.in;

import com.dhbw.backend.AuthenticationToken;
import com.dhbw.backend.user.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/")
    public String test(){
        return "Hello";
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody Token token) {
        Authentication authentication = authenticationManager
                .authenticate( new UsernamePasswordAuthenticationToken(token.token(), ""));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
        return new ResponseEntity<>("User login successfully!...", HttpStatus.OK);
    }
}
