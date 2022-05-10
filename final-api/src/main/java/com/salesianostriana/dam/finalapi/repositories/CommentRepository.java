package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
