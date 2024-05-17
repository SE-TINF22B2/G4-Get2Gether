package com.dhbw.get2gether.backend.widget.application;

import com.dhbw.get2gether.backend.AbstractIntegrationTest;
import com.dhbw.get2gether.backend.event.application.EventService;
import com.dhbw.get2gether.backend.user.application.UserService;
import com.dhbw.get2gether.backend.utils.WithMockGuestUser;
import com.dhbw.get2gether.backend.utils.WithMockOAuth2User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

public class CarpoolWidetServiceTest extends AbstractIntegrationTest {
    @MockBean
    private EventService eventService;
    @MockBean
    private UserService userService;
    @MockBean
    private CarpoolWidgetService carpoolWidgetService;

    @Test
    @WithMockOAuth2User
    void shouldCreateWidget(){

    }
    @Test
    @WithMockOAuth2User
    void shouldNotCreateWidgetIfAlreadyExists() {

    }
    @Test
    @WithMockGuestUser
    void shouldNotCreateWidgetIfUserIsGuest() {

    }
    @Test
    @WithMockOAuth2User
    void shouldAddRider() {

    }
    @Test
    @WithMockGuestUser
    void shouldNotAddRiderIfUserIsGuest() {

    }
    @Test
    @WithMockOAuth2User
    void shouldRemoveRider() {

    }
}
