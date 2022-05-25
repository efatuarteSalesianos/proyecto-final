package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.models.Comment;
import com.salesianostriana.dam.finalapi.models.CommentPK;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Optional<Comment> existsById(CommentPK commentPK);

    void deleteById(CommentPK commentPK);
}
