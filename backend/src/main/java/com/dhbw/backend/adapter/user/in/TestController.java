package com.dhbw.backend.adapter.user.in;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/")
    public String test(){
        return "Hello";
    }
}
