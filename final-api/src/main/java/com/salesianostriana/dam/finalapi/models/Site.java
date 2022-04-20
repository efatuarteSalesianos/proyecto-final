package com.salesianostriana.dam.finalapi.models;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "site")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Site {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator",
            parameters = {
                    @Parameter(
                            name = "uuid_gen_strategy_class",
                            value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
                    )
            }
    )
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "UUID")
    private UUID id;

    private String name;
    private String description;
    private String address;
    private String city;
    private String postalCode;
    private String email;
    private String phone;
    private String web;
    private String originalFile;
    private String scaledFile;

    @Enumerated(EnumType.STRING)
    private SiteTypes type;

    @ManyToOne
    private UserEntity propietario;

    @Builder.Default
    @OneToMany(mappedBy = "site", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

//    @Builder.Default
//    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
//    private List<Appointment> appointments = new ArrayList<>();
//
//    @Builder.Default
//    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();

    @PreRemove
    public void preRemove() {
        for (Like like : likes) {
            like.setSite(null);
        }
    }

    /* HELPERS CON PROPIETARIO */

    public void addPropietario(UserEntity p) {
        this.propietario = p;
        p.getNegocios().add(this);
    }

    public void removePropietario(UserEntity p) {
        p.getNegocios().remove(this);
        this.propietario = null;
    }

//    //add appointment to site
//    public void addAppointmennt(Appointment appointment) {
//        this.appointments.add(appointment);
//    }
//
//    //add comment to site
//    public void addComment(Comment comment) {
//        this.comments.add(comment);
//    }
}
