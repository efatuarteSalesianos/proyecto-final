package com.salesianostriana.dam.finalapi.models;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Comment implements Serializable {

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

    private String title;
    private String description;
    private double rate;
    private String originalFile;
    private String scaledFile;
}
