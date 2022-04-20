package com.salesianostriana.dam.finalapi.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "site_likes")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Like {

    @Builder.Default
    @EmbeddedId
    private LikePK id = new LikePK();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("cliente_id")
    @JoinColumn(name = "cliente_id")
    private UserEntity cliente;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("site_id")
    @JoinColumn(name = "site_id")
    private Site site;

    @CreatedDate
    private LocalDateTime createdDate;

    public void addLikeToUser(UserEntity userEntity) {

        if (userEntity.getLikes() == null) {
            userEntity.setLikes(new ArrayList<>());
            userEntity.getLikes().add(this);
        }
    }

    /* HELPERS CON SITE */

    public void removeLikeFromUser(UserEntity userEntity) {
        if (userEntity.getLikes() != null) {
            userEntity.getLikes().remove(this);
        }
    }

    public void addLikeToSite(Site site) {
        if (site.getLikes() == null) {
            site.setLikes(new ArrayList<>());
            site.getLikes().add(this);
        }
    }

    public void removeLikeFromSite(Site site) {
        if (site.getLikes() != null) {
            site.getLikes().remove(this);
        }
    }
}