package com.salesianostriana.dam.finalapi.dtos.user;

import com.salesianostriana.dam.finalapi.dtos.site.SiteDtoConverter;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserDtoConverter {

    private final SiteDtoConverter siteDtoConverter;

    public GetUserDto toGetUserDto(UserEntity userEntity) {
        return GetUserDto.builder()
                .full_name(userEntity.getFull_name())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .avatar(userEntity.getAvatar())
                .birthDate(userEntity.getBirthDate())
                .phone(userEntity.getPhone())
                .rol(userEntity.getRol().getValue())
                .build();
    }

    public GetPropietarioDto toGetPropietarioDto(UserEntity user) {
        return GetPropietarioDto.builder()
                .full_name(user.getFull_name())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .num_negocios(user.getNegocios().size())
                .negocios(user.getNegocios().stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList()))
                .build();
    }
}