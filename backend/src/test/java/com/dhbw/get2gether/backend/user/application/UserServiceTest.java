package com.dhbw.get2gether.backend.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.dhbw.get2gether.backend.AbstractIntegrationTest;
import com.dhbw.get2gether.backend.user.adapter.out.UserRepository;
import com.dhbw.get2gether.backend.user.model.Guest;
import com.dhbw.get2gether.backend.user.model.User;
import com.dhbw.get2gether.backend.utils.WithMockGuestUser;
import com.dhbw.get2gether.backend.utils.WithMockOAuth2User;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;

class UserServiceTest extends AbstractIntegrationTest {

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Test
    @WithMockOAuth2User
    void shouldGetOwnUserById() {
        // given
        OAuth2User principal = (OAuth2User)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User givenUser =
                User.builder().id("test").email(principal.getAttribute("email")).build();

        when(userRepository.getById(eq(givenUser.getId()))).thenReturn(givenUser);

        // when
        User user = userService.getUserById(givenUser.getId());

        // then
        assertThat(user).isEqualTo(givenUser);
    }

    @Test
    @WithMockOAuth2User
    void shouldNotGetUserByIdIfIsDifferentUser() {
        // given
        User givenUser =
                User.builder().id("foo").email("some.other.mail@example.com").build();

        when(userRepository.getById(eq(givenUser.getId()))).thenReturn(givenUser);

        // when
        // then
        assertThatThrownBy(() -> userService.getUserById(givenUser.getId())).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockGuestUser
    void shouldNotGetUserByIdIfUserIsGuest() {
        // given
        User givenUser = User.builder().id("test").email("test@example.com").build();

        when(userRepository.getById(eq(givenUser.getId()))).thenReturn(givenUser);

        // when
        // then
        assertThatThrownBy(() -> userService.getUserById(givenUser.getId())).isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User
    void shouldNotDeleteUserIfNotAdmin() {
        // given
        User givenUser = User.builder().id("test").email("test@example.com").build();

        doNothing().when(userRepository).deleteById(eq(givenUser.getId()));

        // when
        // then
        assertThatThrownBy(() -> userService.deleteUserById(givenUser.getId()))
                .isInstanceOf(AccessDeniedException.class);
    }

    @Test
    @WithMockOAuth2User(authorities = {"ROLE_ADMIN"})
    void shouldDeleteUserIfAdmin() {
        // given
        User givenUser = User.builder().id("test").email("test@example.com").build();

        doNothing().when(userRepository).deleteById(eq(givenUser.getId()));

        // when
        userService.deleteUserById(givenUser.getId());

        // then
        verify(userRepository).deleteById(givenUser.getId());
    }

    @Test
    @WithMockOAuth2User
    void shouldFindUserByPrincipalIfIsUser() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User givenUser = User.builder().id("test").email("test@example.com").build();

        when(userRepository.findByEmail(eq(givenUser.getEmail()))).thenReturn(Optional.of(givenUser));

        // when
        Optional<User> user = userService.findUserFromPrincipal(principal);

        // then
        assertThat(user).get().isEqualTo(givenUser);
    }

    @Test
    @WithMockGuestUser
    void shouldFindUserByPrincipalIfIsGuest() {
        // given
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal)
                SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // when
        Optional<User> user = userService.findUserFromPrincipal(principal);

        // then
        assertThat(user).get().isInstanceOf(Guest.class);
    }
}
