package com.dhbw.get2gether.backend.user.application;


import com.dhbw.get2gether.backend.user.model.CreateUserCommand;
import com.dhbw.get2gether.backend.user.model.UpdateUserCommand;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.user.adapter.out.UserRepository;
import com.dhbw.get2gether.backend.user.application.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(@Autowired UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public User getUserById(String id) {
        return userRepository.getById(id);
    }

    public void deleteUserById(String id){
        userRepository.deleteById(id);
    }

    public User createUser(CreateUserCommand command) {
        User user = userMapper.mapToUser(command)
                .toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .build();
        return userRepository.insert(user);
    }

    public User updateUser(UpdateUserCommand command) {
        if (!userRepository.existsById(command.getId()))
            throw new IllegalArgumentException("User with ID '" + command.getId() + "' does not exist.");
        User user = userMapper.mapToUser(command);
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public Optional<User> findUserFromPrincipal(OAuth2User principal) {
        if (principal == null) {
            return Optional.empty();
        }
        return findUserByEmail(principal.getAttribute("email"));
    }

    public User getUserByPrincipal(OAuth2User principal) {
        return findUserFromPrincipal(principal).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
