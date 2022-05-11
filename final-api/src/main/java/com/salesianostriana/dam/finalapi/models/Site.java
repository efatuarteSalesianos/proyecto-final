package com.salesianostriana.dam.finalapi.models;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.*;

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
    Set<DayOfWeek> daysOpen = new HashSet<>(Arrays.asList(
            MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY));
    private LocalDateTime openingHour;
    private LocalDateTime closingHour;
    private String originalFile;
    private String scaledFile;

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
