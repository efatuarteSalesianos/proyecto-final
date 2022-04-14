package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findFirstByUsername(String username);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}