package com.salesianostriana.dam.finalapi.users.dto;

import com.salesianostriana.dam.finalapi.users.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .id(user.getId())
                .full_name(user.getFull_name())
                .direccion(user.getDireccion())
                .telefono(user.getTelefono())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .fecha_nacimiento(user.getFecha_nacimiento())
                .build();
    }

    public GetBasicUserDto convertUserEntityToGetBasicUserDto(UserEntity user) {
        return GetBasicUserDto.builder()
                .id(user.getId())
                .full_name(user.getFull_name())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .build();
    }

    public UserEntity convertCreateUserDtoToUserEntity(CreateUserDto newUser) {
        return UserEntity
                .builder()
                .full_name(newUser.getFull_name())
                .direccion(newUser.getDireccion())
                .telefono(newUser.getTelefono())
                .email(newUser.getEmail())
                .nickname(newUser.getNickname())
                .avatar(newUser.getAvatar())
                .password(newUser.getPassword())
                .fecha_nacimiento(newUser.getFecha_nacimiento())
                .build();
    }

    public UserEntity convertSaveUserDtoToUserEntity(SaveUserDto user) {
        return UserEntity
                .builder()
                .full_name(user.getFull_name())
                .direccion(user.getDireccion())
                .telefono(user.getTelefono())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .fecha_nacimiento(user.getFecha_nacimiento())
                .build();
    }

}