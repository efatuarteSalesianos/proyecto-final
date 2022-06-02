package com.salesianostriana.dam.finalapi.security.dto;

import com.salesianostriana.dam.finalapi.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtUserConverter {

    public JwtUserResponse convertUserToJwtUserResponse(UserEntity user, String jwt) {
        return JwtUserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .avatar(user.getAvatar())
                .rol(user.getRol().getValue())
                .token(jwt)
                .build();
    }

}
