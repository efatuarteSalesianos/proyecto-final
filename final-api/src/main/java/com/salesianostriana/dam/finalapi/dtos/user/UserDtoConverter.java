package com.salesianostriana.dam.finalapi.dtos.user;

import com.salesianostriana.dam.finalapi.models.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {
    public GetUserDto toGetUserDto(UserEntity userEntity) {
        return GetUserDto.builder()
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .avatar(userEntity.getAvatar())
                .birthDate(userEntity.getBirthDate())
                .phone(userEntity.getPhone())
                .rol(userEntity.getRol().getValue())
                .build();
    }
}
