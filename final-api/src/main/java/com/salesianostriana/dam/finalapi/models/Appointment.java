package com.salesianostriana.dam.finalapi.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "appointments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date date;
    private User user;
    private Site site;
    private String description;
    private String status;
}
