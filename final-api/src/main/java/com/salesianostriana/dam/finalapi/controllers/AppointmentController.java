package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import com.salesianostriana.dam.finalapi.repositories.AppointmentRepository;
import com.salesianostriana.dam.finalapi.services.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class AppointmentController {

    private final AppointmentRepository appointmentRepository;

    private final AppointmentService appointmentService;

    @GetMapping("/me")
    public List<GetAppointmentDto> misCitas(@AuthenticationPrincipal UserEntity user) {
        return appointmentService.listAppointmentsOfUser(user.getId());
    }

    @GetMapping("/client/{id}")
    public List<GetAppointmentDto> citasCliente(@PathVariable UUID id) {
        return appointmentService.listAppointmentsOfUser(id);
    }

    @GetMapping("/{id}/cliente/{clienteId}")
    public ResponseEntity<GetAppointmentDto> getAppointment(@PathVariable Long appointmentId, @PathVariable UUID clienteId) {
        return ResponseEntity.ok(appointmentService.getAppointmentByIdAndClienteId(appointmentId, clienteId));
    }}
