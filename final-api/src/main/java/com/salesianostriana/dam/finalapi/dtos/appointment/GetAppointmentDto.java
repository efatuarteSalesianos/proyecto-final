package com.salesianostriana.dam.finalapi.dtos.appointment;

import com.salesianostriana.dam.finalapi.models.StatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetAppointmentDto {

    private String cliente;
    private String site;
    private LocalDateTime date;
    private String description;
    private StatusType status;
}
