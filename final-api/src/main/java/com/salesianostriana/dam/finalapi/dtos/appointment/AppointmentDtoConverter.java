package com.salesianostriana.dam.finalapi.dtos.appointment;

import com.salesianostriana.dam.finalapi.models.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentDtoConverter {

    public com.salesianostriana.dam.finalapi.dtos.appointment.GetCommentDto toGetAppointmentDto(Appointment appointment) {
        return com.salesianostriana.dam.finalapi.dtos.appointment.GetCommentDto.builder()
                .cliente(appointment.getCliente().getFull_name())
                .site(appointment.getSite().getName())
                .date(appointment.getDate())
                .description(appointment.getDescription())
                .build();
    }
}
