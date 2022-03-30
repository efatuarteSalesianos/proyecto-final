package com.salesianostriana.dam.finalapi.users.repositories;

import com.salesianostriana.dam.finalapi.users.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByNickname(String nick);

    boolean existsByNickname(String nickname);

}
