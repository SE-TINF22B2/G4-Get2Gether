package com.dhbw.get2gether.backend.test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.events.Event;

@RestController
public class UserController2 {

            @Autowired UserService userService;
    @GetMapping(path = "/User")
    public String getUserName(String name){
        User test = userService.findUserByFirstName(name);
        return test.getFirstName();
    }
}
