package com.salesianostriana.dam.finalapi.security.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserResponse {

    private UUID id;
    private String fullName;
    private String username;
    private String avatar;
    private String rol;
    private String token;

}