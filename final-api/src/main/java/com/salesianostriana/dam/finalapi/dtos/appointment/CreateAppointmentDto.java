package com.salesianostriana.dam.finalapi.dtos.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.finalapi.models.StatusType;
import com.salesianostriana.dam.finalapi.validation.simple.anotations.FutureDate;
import lombok.*;

import javax.persistence.Lob;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateAppointmentDto {

    @JsonFormat(pattern = "dd-MM-yyyy")
    @FutureDate(message = "{appointment.date.futureDate}")
    private LocalDateTime date;

    StatusType status = StatusType.ESPERA;

    @Lob
    private String description;

}
