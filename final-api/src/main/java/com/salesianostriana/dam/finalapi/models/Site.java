package com.salesianostriana.dam.finalapi.models;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Site {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Builder.Default
    @OneToMany(mappedBy = "site", cascade = CascadeType.ALL)
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
