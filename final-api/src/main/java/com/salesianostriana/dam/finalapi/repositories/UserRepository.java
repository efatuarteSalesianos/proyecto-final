package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.models.Rol;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    //find by UserEntity UUID id
    Optional<UserEntity> findById(UUID id);

    Optional<UserEntity> findFirstByUsername(String username);

    boolean existsByUsername(String username);

    List<UserEntity> findUserEntityByRol(Rol rol);
}