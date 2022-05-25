package com.salesianostriana.dam.finalapi.repositories;

import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.models.Appointment;
import com.salesianostriana.dam.finalapi.models.AppointmentPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AppointmentRepository extends JpaRepository<Appointment, AppointmentPK> {

    @Query(value = """
            select new com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto(
                a.cliente.fullName, a.site.name, a.date, a.description, a.status
            )
            from Appointment a
            where a.cliente.id = :clienteId
            """)
    List<GetAppointmentDto> findByClienteId(@Param("clienteId") UUID clienteId);

    @Query(value = """
            select new com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto(
                a.cliente.fullName, a.site.name, a.date, a.description, a.status
            )
            from Appointment a
            where a.cliente.id = :clienteId and a.id = :appointmentId
            """)
    GetAppointmentDto findOneByIdAndClienteId(@Param("appointmentId") Long appointmentId, @Param("clienteId") UUID clienteId);

    Optional<Appointment> findFirstBySiteIdAndClienteId(Long siteId, UUID id);
}
