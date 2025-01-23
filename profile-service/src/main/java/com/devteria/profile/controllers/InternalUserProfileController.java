package com.devteria.profile.controllers;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.RequestContextFilter;

@RestController
@RequestMapping("/internal/users")
@Slf4j
@RequiredArgsConstructor
public class InternalUserProfileController {
    private final UserProfileService service;
    private final RequestContextFilter requestContextFilter;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserProfileCreationResponse createUserProfile(@RequestBody UserProfileCreationRequest request) {
        return service.createUserProfile(request);
    }
}