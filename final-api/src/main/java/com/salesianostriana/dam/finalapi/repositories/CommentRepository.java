package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.models.Comment;
import com.salesianostriana.dam.finalapi.models.CommentPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> existsById(CommentPK commentPK);

    void deleteById(CommentPK commentPK);

    List<Comment> findAllByClienteId(UUID id);
}
