package com.dhbw.get2gether.backend.user.application;

import com.dhbw.get2gether.backend.authentication.GuestAuthenticationPrincipal;
import com.dhbw.get2gether.backend.user.adapter.out.UserRepository;
import com.dhbw.get2gether.backend.user.application.mapper.UserMapper;
import com.dhbw.get2gether.backend.user.model.CreateUserCommand;
import com.dhbw.get2gether.backend.user.model.Guest;
import com.dhbw.get2gether.backend.user.model.UpdateUserCommand;
import com.dhbw.get2gether.backend.user.model.User;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

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

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    public User createUser(CreateUserCommand command) {
        User user = userMapper.mapToUser(command).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .build();
        return userRepository.insert(user);
    }

    public User updateUser(OAuth2User principal, UpdateUserCommand updateUserCommand) {
        User oldUser = getUserByPrincipal(principal);
        User newUser = userMapper.updateUser(oldUser, updateUserCommand);
        return userRepository.save(newUser);
    }

    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findUserFromPrincipal(AuthenticatedPrincipal principal) {
        if (principal instanceof OAuth2User) {
            return findUserByEmail(((OAuth2User) principal).getAttribute("email"));
        } else if (principal instanceof GuestAuthenticationPrincipal guestPrincipal) {
            return Optional.of(new Guest(guestPrincipal.getId(), guestPrincipal.getCreationTime()));
        }
        return Optional.empty();
    }

    public User getUserByPrincipal(AuthenticatedPrincipal principal) {
        return findUserFromPrincipal(principal).orElseThrow(() -> new IllegalArgumentException("User not found"));
    }
}
