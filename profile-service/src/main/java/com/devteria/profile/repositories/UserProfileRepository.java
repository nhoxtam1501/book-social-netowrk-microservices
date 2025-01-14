package com.devteria.profile.repositories;

import com.devteria.profile.entites.UserProfile;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface UserProfileRepository extends Neo4jRepository<UserProfile, String> {
}
