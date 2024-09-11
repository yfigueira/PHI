package com.example.backend.user.web;

import com.example.backend.user.domain.UserService;
import com.example.backend.user.web.dto.UserSummary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static jakarta.transaction.Transactional.TxType.*;

@RestController
@RequestMapping("users")
@Transactional(REQUIRES_NEW)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("search")
    public List<UserSummary> search(
            @RequestParam("search") String searchParam,
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        var result = service.search(searchParam, PageRequest.of(page, size));

        return result.stream()
                .map(UserSummary.mapper()::toDto)
                .collect(Collectors.toList());
    }
}
