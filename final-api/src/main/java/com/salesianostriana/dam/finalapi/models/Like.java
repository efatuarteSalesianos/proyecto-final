package com.salesianostriana.dam.finalapi.models;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "likes")
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Like implements Serializable {

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

    public void addLikeToCliente(UserEntity cliente) {
        this.cliente = cliente;
        if(cliente.getLikes() == null) {
            cliente.setLikes(new ArrayList<>());
            cliente.getLikes().add(this);
        }
    }

    public void addLikeToSite(Site site) {
        this.site = site;
        site.getLikes().add(this);
    }
}