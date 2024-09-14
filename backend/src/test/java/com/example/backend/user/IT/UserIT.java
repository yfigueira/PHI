package com.example.backend.user.IT;

import com.example.backend.IntegrationTest;
import com.example.backend.user.persistence.UserEntity;
import com.example.backend.user.persistence.UserJpaRepository;
import com.example.backend.user.web.dto.UserSummary;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;
import java.util.UUID;

import static com.example.backend.testcontainer.KeycloakContainerManager.*;
import static com.example.backend.testcontainer.KeycloakContainerManager.createUserIfNotExists;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserIT extends IntegrationTest {

    private static final String PATH = "/api/users/search";

    private final UserJpaRepository userJpaRepository;

    @LocalServerPort
    private Integer port;

    @Autowired
    public UserIT(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @BeforeAll
    static void init() {
        createUserIfNotExists(keycloak, KeycloakTestUser.user());
    }

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        populateDatabase();
    }

    @AfterEach
    void tearDown() {
        userJpaRepository.deleteAll();
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
    void search_whenUsersMatchSearchParam_shouldReturnListOfUserSummaryWithStatusOk() {
        var token =  getAccessToken(KeycloakTestUser.user());

        given()
                .auth().oauth2(token)
                .params("search", "first user", "page", 0, "size", 5)
                .when().get(PATH)
                .then()
                .statusCode(200)
                .body("", hasSize(1))
                .and().body("[0].firstName", is(equalTo("First")))
                .and().body("[0].lastName", is(equalTo("User")))
                .and().body("[0].email", is(equalTo("firstemail@email.com")));

        given()
                .auth().oauth2(token)
                .params("search", "user", "page", 0, "size", 5)
                .when().get(PATH)
                .then()
                .statusCode(200)
                .body("", hasSize(2));

        given()
                .auth().oauth2(token)
                .params("search", "unmatched", "page", 0, "size", 5)
                .when().get(PATH)
                .then()
                .statusCode(200)
                .body("", emptyCollectionOf(UserSummary.class));
    }

    @Test
    void search_whenResultIsLargerThanPageSize_shouldReturnLimitedResult() {
        var token =  getAccessToken(KeycloakTestUser.user());
        var paramWithMultipleMatches = "email";

        given()
                .auth().oauth2(token)
                .params("search", paramWithMultipleMatches, "page", 0, "size", 1)
                .when().get(PATH)
                .then()
                .statusCode(200)
                .body("", hasSize(1));
    }

    private void populateDatabase() {
        var entity1 = new UserEntity();
        entity1.setAuthId(UUID.randomUUID());
        entity1.setFirstName("First");
        entity1.setLastName("User");
        entity1.setEmail("firstemail@email.com");

        var entity2 = new UserEntity();
        entity2.setAuthId(UUID.randomUUID());
        entity2.setFirstName("Second");
        entity2.setLastName("User");
        entity2.setEmail("secondemail@email.com");

        userJpaRepository.saveAll(List.of(entity1, entity2));
    }
}
