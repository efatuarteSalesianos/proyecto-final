package com.salesianostriana.dam.finalapi.users.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetUserDto {

    private UUID id;
    private String full_name, direccion, telefono, email, nickname, avatar;
    private LocalDate fecha_nacimiento;

}
