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
@Table(name = "comments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable {

    @Builder.Default
    @EmbeddedId
    private CommentPK id = new CommentPK();

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

    private String title;
    private String description;
    private double rate;
    private String originalFile;
    private String scaledFile;

    public void addCommentToCliente(UserEntity cliente) {
        this.cliente = cliente;
        if(cliente.getComments() == null) {
            cliente.setComments(new ArrayList<>());
            cliente.getComments().add(this);
        }
    }

    public void addCommentToSite(Site site) {
        this.site = site;
        site.getComments().add(this);
    }
}
