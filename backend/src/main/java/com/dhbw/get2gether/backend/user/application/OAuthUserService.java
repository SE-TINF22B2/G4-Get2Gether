package com.dhbw.get2gether.backend.user.application;

import com.dhbw.get2gether.backend.user.model.CreateUserCommand;
import com.dhbw.get2gether.backend.user.model.UpdateUserCommand;
import com.dhbw.get2gether.backend.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OAuthUserService extends DefaultOAuth2UserService {

    private final UserService userService;

    public OAuthUserService(@Autowired UserService userService) {
        this.userService = userService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        processOAuthUser(oAuth2User);
        return oAuth2User;
    }

    private void processOAuthUser(OAuth2User oAuth2User) {
        String mail = oAuth2User.getAttribute("email");
        Optional<User> optionalUser = userService.findUserByEmail(mail);
        optionalUser.ifPresentOrElse(
                user -> updateUser(oAuth2User, createUpdateCommand(user, oAuth2User)),
                () -> createNewUser(oAuth2User)
        );
    }

    private UpdateUserCommand createUpdateCommand(User user, OAuth2User oAuth2User) {
        return UpdateUserCommand.builder()
                .firstName(oAuth2User.getAttribute("given_name"))
                .lastName(oAuth2User.getAttribute("family_name"))
                .email(oAuth2User.getAttribute("email"))
                .profilePictureUrl(oAuth2User.getAttribute("picture"))
                .build();
    }

    private void updateUser(OAuth2User principal, UpdateUserCommand updateUserCommand) {
        userService.updateUser(principal, updateUserCommand);
    }

    private void createNewUser(OAuth2User oAuth2User) {
        CreateUserCommand command = CreateUserCommand.builder()
                .firstName(oAuth2User.getAttribute("given_name"))
                .lastName(oAuth2User.getAttribute("family_name"))
                .email(oAuth2User.getAttribute("email"))
                .profilePictureUrl(oAuth2User.getAttribute("picture"))
                .build();
        userService.createUser(command);
    }
}
