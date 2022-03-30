package com.salesianostriana.dam.finalapi.users.controllers;

import com.salesianostriana.dam.finalapi.errores.excepciones.BadRequestException;
import com.salesianostriana.dam.finalapi.users.dto.CreateUserDto;
import com.salesianostriana.dam.finalapi.users.dto.GetUserDto;
import com.salesianostriana.dam.finalapi.users.dto.SaveUserDto;
import com.salesianostriana.dam.finalapi.users.dto.UserDtoConverter;
import com.salesianostriana.dam.finalapi.users.models.UserEntity;
import com.salesianostriana.dam.finalapi.users.services.UserEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Tag(name="UserEntity", description = "Clase controladora de los usuario")
public class UserController {

    private final UserEntityService userEntityService;

    private final UserDtoConverter userDtoConverter;

    @Operation(summary = "Se muestra la información del usuario logueado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestra correctamente la información del usuario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<?> quienSoy(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(user));
    }

    @Operation(summary = "Se añade un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> newUser(@Parameter(description = "El cuerpo con los atributos del nuevo usuario") @RequestPart("user") CreateUserDto newUser, @RequestPart("file") MultipartFile file) throws BadRequestException {
        UserEntity saved = userEntityService.save(newUser, file);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }

    @Operation(summary = "Se obtiene el perfil de un usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestra el perfil del usuario",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "404",
                    description = "La cuenta del usuario al que buscamos tiene la cuenta privada y no seguimos a esa cuenta",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @GetMapping("/profile/{id}")
    public GetUserDto findProfileById(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user) {
        return userEntityService.findUserProfileById(id, user);
    }

    @Operation(summary = "Se edita el perfil del usuario logueado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestra el perfil del usuario con los datos editados",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Los datos son erróneos",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @PutMapping("/profile/me")
    public GetUserDto editMyProfile(@AuthenticationPrincipal UserEntity logueado, @RequestPart("save_user") SaveUserDto user, @RequestPart("file") MultipartFile file) {
        return userEntityService.editProfile(logueado, user, file);
    }

}
