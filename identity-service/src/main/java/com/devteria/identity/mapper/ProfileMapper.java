package com.devteria.identity.mapper;

import com.devteria.identity.dto.request.UserProfileCreationRequest;
import com.devteria.identity.dto.request.UserCreationRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    UserProfileCreationRequest toProfile(UserCreationRequest request);
}
