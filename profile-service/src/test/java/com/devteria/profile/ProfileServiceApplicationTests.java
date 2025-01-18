package com.devteria.profile;

import com.devteria.profile.controllers.UserProfileController;
import com.devteria.profile.dto.request.UserProfileCreationRequest;
import com.devteria.profile.entites.UserProfile;
import com.devteria.profile.repositories.UserProfileRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class ProfileServiceApplicationTests {
    @Container
    private static Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>(DockerImageName.parse("neo4j:latest"))
            .withoutAuthentication(); // Disable password
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserProfileRepository userProfileRepository;
    @Autowired
    private UserProfileController controller;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
    }


    @Test
    void contextLoads() throws Exception {
        assertThat(controller).isNotNull();
    }


    @Test
    void shouldCreateThenGetUserProfile() throws Exception {
        //*context-path must not to specify in urlTemplate
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createUserProfile())))
                .andExpect(status().isCreated());
        assertThat(userProfileRepository.findAll().size()).isGreaterThan(0);
        UserProfile userProfile = userProfileRepository.findAll().get(userProfileRepository.findAll().size() - 1);
        assertThat(userProfile.getFirstName()).isEqualTo("Lena");
    }

    private UserProfileCreationRequest createUserProfile() {
        return UserProfileCreationRequest.builder()
                .firstName("Lena")
                .lastName("Supa")
                .city("New Mexico")
                .dob(LocalDate.of(1989, 2, 13))
                .build();
    }

}
