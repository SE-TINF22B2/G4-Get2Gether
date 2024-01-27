package com.dhbw.get2gether.backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.OAuthFlow;
import io.swagger.v3.oas.annotations.security.OAuthFlows;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Get2Gether", version = "v1"),
        servers = {
            @Server(url = "/", description = "self"),
            @Server(url = "http://localhost:8080", description = "local")
        })
@SecurityScheme(
        name = "oauth",
        type = SecuritySchemeType.OAUTH2,
        flows =
                @OAuthFlows(
                        implicit = @OAuthFlow(authorizationUrl = "http://localhost:8080/oauth2/authorization/google")))
public class OpenApiConfig {}
