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
@Table(name = "appointments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Appointment implements Serializable {

    @Builder.Default
    @EmbeddedId
    private AppointmentPK id = new AppointmentPK();

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

    private String date;
    private String hour;
    private String description;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    public void addAppointmentToCliente(UserEntity cliente) {
        this.cliente = cliente;
        if(cliente.getAppointments() == null) {
            cliente.setAppointments(new ArrayList<>());
            cliente.getAppointments().add(this);
        }
    }

    public void addAppointmentToSite(Site site) {
        this.site = site;
        site.getAppointments().add(this);
    }
}
