package com.example.backend.testcontainer;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public class KeycloakContainerManager {

    private static final String REALM = "phi-iam";

    public static void createUserIfNotExists(KeycloakContainer keycloak, KeycloakTestUser testUser) {
        var createdUsers = getKeycloakUsers(keycloak)
                .search(testUser.username());

        var userRepresentation = getUserRepresentation(testUser);

        if (!createdUsers.contains(userRepresentation)) {
            createKeycloakUser(keycloak, userRepresentation);
        }
    }

    private static void createKeycloakUser(KeycloakContainer keycloak, UserRepresentation userRepresentation) {
        getKeycloakUsers(keycloak).create(userRepresentation);
    }

    private static UsersResource getKeycloakUsers(KeycloakContainer keycloak) {
        return keycloak.getKeycloakAdminClient()
                .realm(REALM)
                .users();
    }

    private static UserRepresentation getUserRepresentation(KeycloakTestUser testUser) {
        var userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(testUser.firstName());
        userRepresentation.setLastName(testUser.lastName());
        userRepresentation.setEmail(testUser.email());
        userRepresentation.setUsername(testUser.username());
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(true);

        var credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(testUser.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);

        userRepresentation.setCredentials(List.of(credentialRepresentation));
        return userRepresentation;
    }

    public record KeycloakTestUser(
            String firstName,
            String lastName,
            String email,
            String username,
            String password
    ) {
        public static KeycloakTestUser user() {
            return new KeycloakTestUser(
                    "Test",
                    "User",
                    "testuser@email.com",
                    "testuser@email.com",
                    "user-secret"
            );
        }

        public static KeycloakTestUser admin() {
            return new KeycloakTestUser(
                    "Test",
                    "Admin",
                    "testadmin@email.com",
                    "testadmin@email.com",
                    "admin-secret"
            );
        }
    }
}
