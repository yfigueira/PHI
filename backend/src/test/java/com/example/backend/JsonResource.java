package com.example.backend;

public enum JsonResource {

    USER_AUTHENTICATION_REQUEST("security/user-authentication-request.json"),
    USER_AUTHENTICATION_RESPONSE("security/user-authentication-response.json"),
    USER_AUTHENTICATION_USERNAME_NOT_FOUND("security/user-authentication-username-not-found.json");

    private final String path;

    JsonResource(String path) {
        this.path = path;
    }

    public String path() {
        return this.path;
    }
}
