package io.github.marcelobaldi.ApiRestJwt.repository;

import io.github.marcelobaldi.ApiRestJwt.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findOneByEmail(String email);
    Boolean existsByEmail(String email);
}
