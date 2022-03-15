package com.salesianostriana.dam.finalapi.security.dto;

import com.salesianostriana.dam.finalapi.users.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class JwtUserConverter {

    public JwtUserResponse convertUserToJwtUserResponse(UserEntity user, String jwt) {
        return JwtUserResponse.builder()
                .id(user.getId())
                .fullName(user.getFull_name())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .rol(user.getRol().getValue())
                .token(jwt)
                .build();
    }

}
