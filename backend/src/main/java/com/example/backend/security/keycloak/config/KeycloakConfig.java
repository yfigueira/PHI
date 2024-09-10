package com.example.backend.security.keycloak.config;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {

    private static final String GRANT_TYPE = "client_credentials";

    @Value("${app.keycloak.server-url}")
    private String serverUrl;

    @Value("${app.keycloak.realm}")
    private String realm;

    @Value("${app.keycloak.iam-client.client-id}")
    private String clientId;

    @Value("${app.keycloak.iam-client.client-secret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .grantType(GRANT_TYPE)
                .build();
    }
}
