package com.salesianostriana.dam.finalapi.models;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static java.time.DayOfWeek.*;

@Entity
@Table(name = "site")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Site {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;
    private String address;
    private String city;
    private String postalCode;
    private String email;
    private String phone;
    private String web;
    @Builder.Default
    private EnumSet<DayOfWeek> daysOpen = EnumSet.of(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY);
    private LocalTime openingHour;
    private LocalTime closingHour;
    private String originalFile;
    private String scaledFile;
    private boolean liked;

    @Enumerated(EnumType.STRING)
    private SiteTypes type;

    @ManyToOne
    private UserEntity propietario;

    @Builder.Default
    @OneToMany(mappedBy = "site", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Like> likes = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<Appointment> appointments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

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

    //add appointment to site
    public void addAppointmennt(Appointment appointment) {
        this.appointments.add(appointment);
    }

    //add comment to site
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }
}
