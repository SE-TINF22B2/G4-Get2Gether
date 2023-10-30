package com.dhbw.get2gether.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.NullRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {
    @Autowired
    private CustomAuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        RequestCache nullRequestCache = new NullRequestCache();

        http
                .csrf()
                .disable()
                .authorizeHttpRequests(requests -> {
                            requests
                                    .requestMatchers("/login").permitAll()
                                    .requestMatchers("/**").authenticated();
                        }
                        )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.NEVER)
                //Required for not giving out a session when authethication fails
                ).requestCache((cache) -> cache
                        .requestCache(nullRequestCache)
                )
//                .securityContext((securityContext) -> securityContext
//                        .requireExplicitSave(true)
//                )
                .cors()
                .configurationSource(corsConfigurationSource())
        ;


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.authenticationProvider(authProvider);
        return authenticationManagerBuilder.build();
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        List<String> domains = List.of("**/**");
        configuration.setAllowCredentials(true);
        configuration.setAllowedOriginPatterns(domains);
        configuration.setAllowedMethods(
                List.of(
                        HttpMethod.GET.name(),
                        HttpMethod.PUT.name(),
                        HttpMethod.POST.name(),
                        HttpMethod.DELETE.name()));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }

}
