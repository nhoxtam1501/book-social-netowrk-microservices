package com.devteria.identity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileCreationRequest {
    private String userId;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String city;
}
