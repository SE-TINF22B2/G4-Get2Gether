package com.dhbw.get2gether.backend.user.application;


import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.user.adapter.out.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    private final UserRepository userRepository;

    public UserService(@Autowired UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(String id) {
        return userRepository.getById(id);
    }
}
