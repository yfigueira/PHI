package com.example.backend.friendrequest.web;

import com.example.backend.friendrequest.domain.FriendRequestService;
import com.example.backend.friendrequest.web.dto.FriendRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static jakarta.transaction.Transactional.TxType.*;

@RestController
@RequestMapping(FriendRequestController.PATH)
@Transactional(REQUIRES_NEW)
@RequiredArgsConstructor
public class FriendRequestController {

    public static final String PATH = "friend-requests";

    private final FriendRequestService service;

    @PostMapping
    public FriendRequestDto create(@RequestBody FriendRequestDto dto) {
        var friendRequest = FriendRequestDto.mapper().toDomain(dto);
        var created = service.create(friendRequest);
        return FriendRequestDto.mapper().toDto(created);
    }
}
