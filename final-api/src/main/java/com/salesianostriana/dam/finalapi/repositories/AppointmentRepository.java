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

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    //exist by appointmentPK
    Optional<Appointment> existById(AppointmentPK appointmentPK);

    //delete by appointmentPK
    void deleteById(AppointmentPK appointmentPK);

    // Listar todos los appointments de un usuario por su id
    @Query("""
            select new com.salesianostriana.dam.dtos.appointment.GetAppointmentDto(
                a.id, a.site, a.date, a.description, a.status)
            )
            from Appointment a
            where a.cliente.id = :clienteId
            """)
    List<GetAppointmentDto> findByClienteId(@Param("clienteId") UUID clienteId);

    // Mostrar la informacion de un appointment por su id de un cliente por su id
    @Query("""
            select new com.salesianostriana.dam.dtos.appointment.GetAppointmentDto(
                a.id, a.site, a.date, a.description, a.status)
            )
            from Appointment a
            where a.cliente.id = :clienteId and a.id = :appointmentId
            """)
    GetAppointmentDto findOneByIdAndClienteId(@Param("appointmentId") Long appointmentId, @Param("clienteId") UUID clienteId);

    Optional<Appointment> findFirstBySiteIdAndClienteId(Long siteId, UUID id);
}
