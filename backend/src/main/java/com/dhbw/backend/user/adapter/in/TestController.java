package com.dhbw.backend.user.adapter.in;

import com.dhbw.backend.user.model.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private AuthenticationManager authenticationManager;

    //Needed for storing the session information
//    private SecurityContextRepository securityContextRepository =
//            new HttpSessionSecurityContextRepository();
//    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    @GetMapping("/")
    public String test() {
        return "Hello";
    }

    @PostMapping("/login")
    public ResponseEntity<Token> authenticateUser(@RequestBody Token token, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(token.token(), ""));
        if (authentication != null) {
            System.out.println("Authenticated");
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            //Storing the session information
//            SecurityContext context = securityContextHolderStrategy.createEmptyContext();
//            context.setAuthentication(authentication);
//            this.securityContextHolderStrategy.setContext(context);
//            securityContextRepository.saveContext(context, request, response);
            return new ResponseEntity<>(new Token("passt"), HttpStatus.OK);
        }
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

    }

    @GetMapping("/any")
    public ResponseEntity<Token> any() {
        return new ResponseEntity<>(new Token("Works"), HttpStatus.OK);
    }
}
