package com.example.backend;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Map;

import static com.example.backend.testcontainer.KeycloakContainerManager.*;
import static io.restassured.RestAssured.given;

@Testcontainers
@ActiveProfiles("test")
public abstract class IntegrationTest {

    @Container
    @ServiceConnection
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16.0-alpine"));

    @Container
    protected static final KeycloakContainer keycloak = new KeycloakContainer().withRealmImportFile("/keycloak-realm-config.json");


    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;
    @Value("${app.keycloak.iam-client.client-id}")
    private String clientId;
    @Value("${app.keycloak.iam-client.client-secret}")
    private String clientSecret;


    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("app.keycloak.server-url", keycloak::getAuthServerUrl);
    }

    protected String getAccessToken(KeycloakTestUser testUser) {
        return given().contentType("application/x-www-form-urlencoded")
                .formParams(Map.of(
                        "username", testUser.username(),
                        "password", testUser.password(),
                        "grant_type", "password",
                        "scope", "openid",
                        "client_id", clientId,
                        "client_secret", clientSecret
                ))
                .post(issuerUri + "/protocol/openid-connect/token")
                .then().assertThat().statusCode(200)
                .extract().path("access_token");
    }
}

