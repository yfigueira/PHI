package com.example.backend.friendrequest.IT;

import com.example.backend.IntegrationTest;
import com.example.backend.friendrequest.domain.RequestStatus;
import com.example.backend.friendrequest.persistence.FriendRequestJpaRepository;
import com.example.backend.friendrequest.web.dto.FriendRequestDto;
import com.example.backend.security.auth.web.dto.UserAuthenticationDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.UUID;

import static com.example.backend.testcontainer.KeycloakContainerManager.*;
import static com.example.backend.testcontainer.KeycloakContainerManager.createUserIfNotExists;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FriendRequestIT extends IntegrationTest {

    private static final String PATH = "/api/friend-requests";

    private final FriendRequestJpaRepository friendRequestJpaRepository;

    @LocalServerPort
    private Integer port;

    @Autowired
    public FriendRequestIT(FriendRequestJpaRepository friendRequestJpaRepository) {
        this.friendRequestJpaRepository = friendRequestJpaRepository;
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
        friendRequestJpaRepository.deleteAll();
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
    void create_whenSuccessful_shouldReturnCreatedFriendRequestDto() {
        var token = getAccessToken(KeycloakTestUser.user());
        var senderDto = new UserAuthenticationDto(UUID.randomUUID(), "Sender", "Sender", "sender@email.com");
        var receiverDto = new UserAuthenticationDto(UUID.randomUUID(), "Receiver", "Receiver", "receiver@email.com");

        String senderId = given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(senderDto)
                .when()
                .post("/api/user-auth")
                .then().statusCode(200)
                .extract().path("id");

        String receiverId = given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(receiverDto)
                .when()
                .post("/api/user-auth")
                .then().statusCode(200)
                .extract().path("id");

        var friendRequestBody = FriendRequestDto.builder()
                .sender(UUID.fromString(senderId))
                .receiver(UUID.fromString(receiverId))
                .build();

        given()
                .auth().oauth2(token)
                .contentType(ContentType.JSON)
                .body(friendRequestBody)
                .when().post(PATH)
                .then()
                .statusCode(200)
                .body("sender", is(equalTo(senderId)))
                .body("receiver", is(equalTo(receiverId)))
                .body("status", is(equalTo(RequestStatus.PENDING.name())));
    }
}
