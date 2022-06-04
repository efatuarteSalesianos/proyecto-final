package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import com.salesianostriana.dam.finalapi.repositories.AppointmentRepository;
import com.salesianostriana.dam.finalapi.services.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/appointment")
@Tag(name = "Appointment", description = "Clase controladora de las citas de un cliente")
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    private final AppointmentService appointmentService;

    @Operation(summary = "Método para obtener mis citas", description = "Método para obtener mis citas", tags = "Appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene correctamente el listado de citas.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/me")
    public List<GetAppointmentDto> misCitas(@AuthenticationPrincipal UserEntity user) {
        return appointmentService.listAppointmentsOfUser(user.getId());
    }

    @Operation(summary = "Método para obtener las citas de un cliente", description = "Método para obtener las citas de un cliente", tags = "Appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene correctamente el listado de citas del cliente buscado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existe el usuario que se ha buscado.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/cliente/{clienteId}")
    public List<GetAppointmentDto> citasCliente(@PathVariable UUID clienteId) {
        return appointmentService.listAppointmentsOfUser(clienteId);
    }

    @Operation(summary = "Método para obtener la información de una cita de un cliente", description = "Método para obtener la información de una cita de un cliente", tags = "Appointment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene correctamente la información de la cita buscada.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/{appointmentId}/cliente/{clienteId}")
    public ResponseEntity<GetAppointmentDto> getAppointment(@PathVariable Long appointmentId, @PathVariable UUID clienteId) {
        return ResponseEntity.ok(appointmentService.getAppointmentByIdAndClienteId(appointmentId, clienteId));
    }}
