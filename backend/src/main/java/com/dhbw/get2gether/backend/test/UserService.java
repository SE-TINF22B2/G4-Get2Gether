package com.dhbw.get2gether.backend.test;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final Repo repo;
    public UserService(@Autowired Repo repo) {
        this.repo = repo;
    }
    public User findUserByFirstName(String name){
        User user;
        try {
            //user = repo.findByFirstName(name);
            user = new User("1","2","1","1","1");

        }catch (Exception e){
            user = new User("1","1","1","1","1");
        }
        return user;
    }
}
