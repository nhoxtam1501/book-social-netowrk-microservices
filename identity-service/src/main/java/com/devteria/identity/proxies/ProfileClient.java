package com.devteria.identity.proxies;

import com.devteria.identity.dto.request.UserProfileCreationRequest;
import com.devteria.identity.dto.response.UserProfileCreationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "profile-service", url = "${app.services.profile.url}")
public interface ProfileClient {

    @PostMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    UserProfileCreationResponse createProfile(@RequestBody UserProfileCreationRequest request);

}