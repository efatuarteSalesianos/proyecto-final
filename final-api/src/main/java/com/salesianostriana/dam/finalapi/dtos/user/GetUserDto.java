package com.salesianostriana.dam.finalapi.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserDto {
    private UUID id;
    private String fullName;
    private String username;
    private String email;
    private String avatar;
    private Date birthDate;
    private String phone;
    private String rol;
}
