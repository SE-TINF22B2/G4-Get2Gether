package com.dhbw.get2gether.backend.user.application;

import com.dhbw.get2gether.backend.authentication.GuestAuthenticationPrincipal;
import com.dhbw.get2gether.backend.exceptions.EntityNotFoundException;
import com.dhbw.get2gether.backend.user.adapter.out.UserRepository;
import com.dhbw.get2gether.backend.user.application.mapper.UserMapper;
import com.dhbw.get2gether.backend.user.model.*;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @PostAuthorize("@userAuthorizationService.isAuthorized(principal, returnObject) OR hasRole('ADMIN')")
    public User getUserById(String id) {
        return userRepository.getById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('GUEST')")
    public Optional<User> findUserFromPrincipal(AuthenticatedPrincipal principal) {
        if (principal instanceof OAuth2User) {
            return findUserByEmail(((OAuth2User) principal).getAttribute("email"));
        } else if (principal instanceof GuestAuthenticationPrincipal guestPrincipal) {
            return Optional.of(new Guest(guestPrincipal.getId(), guestPrincipal.getCreationDate()));
        }
        return Optional.empty();
    }

    @PreAuthorize("hasRole('GUEST')")
    public User getUserByPrincipal(AuthenticatedPrincipal principal) {
        return findUserFromPrincipal(principal).orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    @PreAuthorize("hasRole('USER')")
    public User updateUser(AuthenticatedPrincipal principal, UpdateUserCommand updateUserCommand) {
        User oldUser = getUserByPrincipal(principal);
        User newUser = userMapper.mapSettingsCommandToUser(oldUser, updateUserCommand);
        return userRepository.save(newUser);
    }

    @PreAuthorize("hasRole('GUEST')")
    public List<SimpleUserDto> getSimpleUsersById(List<String> userIds) {
        List<SimpleUserDto> simpleUserDtos = new ArrayList<>();
        for(String userId : userIds) {
            userRepository.findById(userId).ifPresent(user -> simpleUserDtos.add(userMapper.mapToSimpleUserDto(user)));
        }
        return simpleUserDtos;
    }

    protected User createUser(CreateUserCommand command) {
        User user = userMapper.mapToUser(command).toBuilder()
                .id(UUID.randomUUID().toString())
                .creationDate(LocalDateTime.now())
                .settings(Settings.builder()
                        .colorMode(ColorMode.LIGHT)
                        .build())
                .build();
        return userRepository.insert(user);
    }

    protected User syncUser(OAuth2User principal, SyncUserCommand syncUserCommand) {
        User oldUser = getUserByPrincipal(principal);
        User newUser = userMapper.mapSyncCommandToUser(oldUser, syncUserCommand);
        return userRepository.save(newUser);
    }

    protected Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
