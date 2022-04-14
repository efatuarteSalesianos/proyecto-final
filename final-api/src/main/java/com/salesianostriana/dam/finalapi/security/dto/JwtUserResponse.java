package com.salesianostriana.dam.finalapi.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtUserResponse {

    private String username;
    private String avatar;
    private String rol;
    private String token;

}