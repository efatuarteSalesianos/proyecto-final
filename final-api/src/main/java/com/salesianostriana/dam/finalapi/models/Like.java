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
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @MapsId("site_id")
    @JoinColumn(name = "site_id")
    private Site site;

    public void addLikeToUser(User user) {

        if (user.getLikes() == null) {
            user.setLikes(new ArrayList<>());
            user.getLikes().add(this);
        }
    }

    public void removeLikeFromUser(User user) {
        if (user.getLikes() != null) {
            user.getLikes().remove(this);
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