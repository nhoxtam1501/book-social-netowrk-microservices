package com.devteria.profile.controllers;

import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.RequestContextFilter;

import java.util.List;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor
public class UserProfileController {
    private final UserProfileService service;
    private final RequestContextFilter requestContextFilter;

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileCreationResponse> getUserProfile(@PathVariable String userId) {
        return ResponseEntity.ok(service.getUserProfile(userId));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserProfileCreationResponse>> getAllUserProfiles() {
        return ResponseEntity.ok(service.getAllUserProfiles());
    }
}
