package com.salesianostriana.dam.finalapi.services;

import com.salesianostriana.dam.finalapi.dtos.appointment.AppointmentDtoConverter;
import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.errores.excepciones.EntityNotFoundException;
import com.salesianostriana.dam.finalapi.models.Appointment;
import com.salesianostriana.dam.finalapi.models.AppointmentPK;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import com.salesianostriana.dam.finalapi.repositories.AppointmentRepository;
import com.salesianostriana.dam.finalapi.repositories.UserRepository;
import com.salesianostriana.dam.finalapi.services.base.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AppointmentService extends BaseService<Appointment, AppointmentPK, AppointmentRepository> {

    private final UserRepository userRepository;

    private final AppointmentRepository appointmentRepository;

    private final AppointmentDtoConverter appointmentDtoConverter;

    public List<GetAppointmentDto> listAppointmentsOfUser(UUID id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if (user.isPresent()) {
            return repository.findByClienteId(id);
        }
        return null;
    }

    public GetAppointmentDto getAppointmentByIdAndClienteId(Long siteId, UUID clienteId) {
        Optional<Appointment> appointment = repository.findById(new AppointmentPK(clienteId, siteId));
        if (appointment.isEmpty()) {
            throw new EntityNotFoundException("No appointment matches the provided information");
        } else {
            return appointmentRepository.findOneByIdAndClienteId(siteId, clienteId);
        }
    }
}
