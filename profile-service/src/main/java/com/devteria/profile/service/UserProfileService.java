package com.devteria.profile.service;

import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.dto.response.UserProfileCreationResponse;
import com.devteria.profile.entites.UserProfile;
import com.devteria.profile.mapper.UserProfileMapper;
import com.devteria.profile.repositories.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserProfileService {
    private final UserProfileRepository repository;
    private final UserProfileMapper mapper;

    public UserProfileCreationResponse createUserProfile(UserProfileCreationRequest request) {
        UserProfile userProfile = mapper.toUserProfile(request);
        userProfile = repository.save(userProfile);
        log.info("UserProfile with userId {} has been created: {}", userProfile.getUserId(), userProfile);
        return mapper.toResponse(userProfile);
    }

    public UserProfileCreationResponse getUserProfile(String userId) {
        UserProfile userProfile = repository.findById(userId).orElseThrow(() -> new RuntimeException("Profile not found"));
        log.info("Found user profile with id : {}", userId);
        return mapper.toResponse(userProfile);
    }
}
