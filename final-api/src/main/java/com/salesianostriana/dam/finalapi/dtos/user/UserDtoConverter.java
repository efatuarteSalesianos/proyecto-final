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
                .id(userEntity.getId())
                .fullName(userEntity.getFullName())
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
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .avatar(user.getAvatar())
                .num_negocios(user.getNegocios().size())
                .negocios(user.getNegocios().stream().map(siteDtoConverter::toGetSiteDto).collect(Collectors.toList()))
                .build();
    }
}
