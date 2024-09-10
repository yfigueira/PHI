package com.example.backend.security.auth.web;

import com.example.backend.security.auth.domain.UserAuthenticationService;
import com.example.backend.security.auth.web.dto.UserAuthenticationDto;
import com.example.backend.security.auth.web.dto.UserAuthenticationSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UserAuthenticationController.PATH)
@RequiredArgsConstructor
public class UserAuthenticationController {

    public static final String PATH = "user-auth";

    private final UserAuthenticationService service;

    @GetMapping("{username}")
    public UserAuthenticationSummary getByUsername(@PathVariable("username") String username) {
        var userAuthentication = service.findByUsername(username);
        return UserAuthenticationSummary.mapper().toDto(userAuthentication);
    }

    @PostMapping
    public UserAuthenticationSummary create(@RequestBody UserAuthenticationDto dto) {
        var userAuthentication = UserAuthenticationDto.mapper().toDomain(dto);
        var created = service.create(userAuthentication);
        return UserAuthenticationSummary.mapper().toDto(created);
    }
}
