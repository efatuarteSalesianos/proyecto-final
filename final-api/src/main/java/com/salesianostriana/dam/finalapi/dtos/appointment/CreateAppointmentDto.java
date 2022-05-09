package com.salesianostriana.dam.finalapi.dtos.appointment;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Lob
    private String description;

}
