package com.salesianostriana.dam.finalapi.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "site_likes")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class Like {
    @Builder.Default
    @EmbeddedId
    private LikePK id = new LikePK();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("user_entity_id")
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("site_id")
    @JoinColumn(name = "site_id")
    private Site site;

    public void addLikeToUser(UserEntity userEntity) {

        if (userEntity.getLikes() == null) {
            userEntity.setLikes(new ArrayList<>());
            userEntity.getLikes().add(this);
        }
    }

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