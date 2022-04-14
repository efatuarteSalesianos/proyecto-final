package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.models.Like;
import com.salesianostriana.dam.finalapi.models.LikePK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface LikeRepository extends JpaRepository<Like, LikePK> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM site_likes  WHERE site_id = :site AND user_id = :user", nativeQuery = true)
    void deleteLike(Long site, UUID user);

    Optional<Like> findFirstBySiteIdAndUserId(Long siteId, UUID userId);

}