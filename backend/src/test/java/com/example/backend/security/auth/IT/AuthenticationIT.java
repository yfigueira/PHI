package com.example.backend.security.auth.IT;

import com.example.backend.IntegrationTest;
import com.example.backend.JsonResource;
import com.example.backend.security.auth.persistence.UserAuthenticationJpaRepository;
import com.example.backend.security.auth.web.dto.UserAuthenticationDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.util.ResourceUtils;

import java.io.IOException;
import java.util.UUID;

import static com.example.backend.testcontainer.KeycloakContainerManager.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthenticationIT extends IntegrationTest {

    private static final String PATH = "api/user-auth";

    private final UserAuthenticationJpaRepository userAuthenticationJpaRepository;
    private final ObjectMapper objectMapper;


    @LocalServerPort
    private Integer port;


    @Autowired
    public AuthenticationIT(UserAuthenticationJpaRepository userAuthenticationJpaRepository, ObjectMapper objectMapper) {
        this.userAuthenticationJpaRepository = userAuthenticationJpaRepository;
        this.objectMapper = objectMapper;
    }

    @BeforeAll
    static void init() {
        createUserIfNotExists(keycloak, KeycloakTestUser.user());
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @AfterEach
    void tearDown() {
        userAuthenticationJpaRepository.deleteAll();
    }

    @Test
    void postgresContainerLoads() {
        assertThat(postgres.isCreated(), is(true));
        assertThat(postgres.isRunning(), is(true));
    }

    @Test
    void keycloakContainerLoads() {
        assertThat(keycloak.isCreated(), is(true));
        assertThat(keycloak.isRunning(), is(true));
    }

    @Test
    void getByUsername_whenUserExists_shouldReturnUserSummaryWithStatusOk() throws IOException {
        var token = getAccessToken(KeycloakTestUser.user());
        var requestBody = getRequestBody();

        given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(PATH)
                .then().assertThat().statusCode(200);

        given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .get("%s/%s".formatted(PATH, KeycloakTestUser.user().username()))
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(JsonResource.USER_AUTHENTICATION_RESPONSE.path()));
    }

    @Test
    void getByUsername_whenUserDoesNotExist_shouldReturnErrorResponseWithStatusNotFound() throws IOException {
        var token = getAccessToken(KeycloakTestUser.user());
        var requestBody = getRequestBody();

        given().
                auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .get("%s/%s".formatted(PATH, KeycloakTestUser.user().username()))
                .then()
                .statusCode(404)
                .body(matchesJsonSchemaInClasspath(JsonResource.USER_AUTHENTICATION_USERNAME_NOT_FOUND.path()));
    }

    @Test
    void create_whenUserCreated_shouldReturnUserAuthenticationSummaryWithStatusOk() throws IOException {
        var token = getAccessToken(KeycloakTestUser.user());
        var requestBody = getRequestBody();

        given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(PATH)
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath(JsonResource.USER_AUTHENTICATION_RESPONSE.path()));
    }

    @Test
    void create_whenUserAuthenticationIsNotValid_shouldReturnErrorResponseWithStatusBadRequest() {
        var token = getAccessToken(KeycloakTestUser.user());
        var requestBody = new UserAuthenticationDto(UUID.randomUUID(), "", "", "");

        given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post(PATH)
                .then()
                .body("", hasSize(3));
    }

    private UserAuthenticationDto getRequestBody() throws IOException {
        return objectMapper.readValue(
                ResourceUtils.getFile("classpath:" + JsonResource.USER_AUTHENTICATION_REQUEST.path()),
                UserAuthenticationDto.class
        );
    }
}
