package com.example.backend.security.auth.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationConverterTest {

    private Map<String, Object> realmAccess = new HashMap<>();
    private Map<String, Object> resourceAccess = new HashMap<>();


    @InjectMocks
    private JwtAuthenticationConverter converter;

    @Mock
    private Jwt jwt;

    @Mock
    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;


    @BeforeEach
    void setUp() {
        realmAccess = Map.of();
        resourceAccess = Map.of();
    }


    @Test
    void convert_whenRealmAndResourceHaveRoles_shouldReturnRealmAndResourceRoles() {
        // given
        realmAccess = Map.of("roles", List.of("admin", "user"));
        resourceAccess = Map.of("account", Map.of("roles", List.of("manage_account")));

        when(jwt.getClaim("realm_access")).thenReturn(realmAccess);
        when(jwt.getClaim("resource_access")).thenReturn(resourceAccess);
        when(jwtGrantedAuthoritiesConverter.convert(jwt)).thenReturn(List.of(new SimpleGrantedAuthority("SCOPE_test")));

        // when
        var authenticationToken = converter.convert(jwt);

        // then
        assertAll(() -> {
            assertThat(authenticationToken, notNullValue());
            assertThat(authenticationToken.getAuthorities(), hasSize(4));
            assertThat(authenticationToken.getAuthorities(), containsInAnyOrder(
                    new SimpleGrantedAuthority("ROLE_admin"),
                    new SimpleGrantedAuthority("ROLE_user"),
                    new SimpleGrantedAuthority("ROLE_manage_account"),
                    new SimpleGrantedAuthority("SCOPE_test")
            ));
        });
    }

    @Test
    void convert_whenOnlyRealmHasRoles_shouldReturnOnlyRealmRoles() {
        // given
        realmAccess = Map.of("roles", List.of("admin", "user"));

        when(jwt.getClaim("realm_access")).thenReturn(realmAccess);
        when(jwt.getClaim("resource_access")).thenReturn(resourceAccess);
        when(jwtGrantedAuthoritiesConverter.convert(jwt)).thenReturn(List.of(new SimpleGrantedAuthority("SCOPE_test")));

        // when
        var authenticationToken = converter.convert(jwt);

        // then
        assertAll(() -> {
            assertThat(authenticationToken, notNullValue());
            assertThat(authenticationToken.getAuthorities(), hasSize(3));
            assertThat(authenticationToken.getAuthorities(), containsInAnyOrder(
                    new SimpleGrantedAuthority("ROLE_admin"),
                    new SimpleGrantedAuthority("ROLE_user"),
                    new SimpleGrantedAuthority("SCOPE_test")
            ));
        });
    }

    @Test
    void convert_whenOnlyResourceHasRoles_shouldReturnOnlyResourceRoles() {
        // given
        resourceAccess = Map.of("account", Map.of("roles", List.of("manage_account")));

        when(jwt.getClaim("realm_access")).thenReturn(realmAccess);
        when(jwt.getClaim("resource_access")).thenReturn(resourceAccess);
        when(jwtGrantedAuthoritiesConverter.convert(jwt)).thenReturn(List.of(new SimpleGrantedAuthority("SCOPE_test")));

        // when
        var authenticationToken = converter.convert(jwt);

        // then
        assertAll(() -> {
            assertThat(authenticationToken, notNullValue());
            assertThat(authenticationToken.getAuthorities(), hasSize(2));
            assertThat(authenticationToken.getAuthorities(), containsInAnyOrder(
                    new SimpleGrantedAuthority("ROLE_manage_account"),
                    new SimpleGrantedAuthority("SCOPE_test")
            ));
        });
    }

    @Test
    void convert_whenJwtHasNoScopesAndNoClaims_shouldReturnEmptyCollectionOfAuthorities() {
        // given
        when(jwt.getClaim("realm_access")).thenReturn(realmAccess);
        when(jwt.getClaim("resource_access")).thenReturn(resourceAccess);
        when(jwtGrantedAuthoritiesConverter.convert(jwt)).thenReturn(List.of());

        // when
        var authenticationToken = converter.convert(jwt);

        // then
        assertAll(() -> {
            assertThat(authenticationToken, notNullValue());
            assertThat(authenticationToken.getAuthorities(), emptyCollectionOf(GrantedAuthority.class));
        });
    }
}