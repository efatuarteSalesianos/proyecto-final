package com.salesianostriana.dam.finalapi.dtos.user;

import com.salesianostriana.dam.finalapi.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public GetUserDto toGetUserDto(User user) {
        return GetUserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .birthDate(user.getBirthDate())
                .phone(user.getPhone())
                .rol(user.getRol().getValue())
                .build();
    }
}
