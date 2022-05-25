package com.salesianostriana.dam.finalapi.dtos.appointment;

import com.salesianostriana.dam.finalapi.models.Appointment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentDtoConverter {

    public GetAppointmentDto toGetAppointmentDto(Appointment appointment) {
        return GetAppointmentDto.builder()
                .cliente(appointment.getCliente().getFullName())
                .site(appointment.getSite().getName())
                .date(appointment.getDate())
                .description(appointment.getDescription())
                .status(appointment.getStatus())
                .build();
    }
}
