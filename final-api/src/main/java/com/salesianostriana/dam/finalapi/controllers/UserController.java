package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.user.*;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import com.salesianostriana.dam.finalapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name="Usuarios", description = "Clase controladora de la seguridad de los usuarios")
public class UserController {
    private final UserService userService;
    private final UserDtoConverter userDtoConverter;

    @Operation(summary = "Método para registrar un usuario.", description = "Método para registrar un usuario, pasándole un fichero json con sus datos y un fichero de imagen.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente el usuario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos del usuario.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> newUser(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                              @RequestPart("file") MultipartFile file) {
        if(!file.isEmpty() || newUser!=null)
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(userService.saveUser(newUser, file)));
        else
            throw new MultipartException("The provided multipart file must not be null");
    }

    @Operation(summary = "Método para dar de alta un administrador.", description = "Método para dar de alta un administrador.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente el administrador.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos del usuario.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> newAdmin(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                               @RequestPart("file") MultipartFile file) {

            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(userService.saveAdmin(newUser, file)));

    }

    @Operation(summary = "Método para dar de alta un propietario.", description = "Método para dar de alta un propietario.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado correctamente el propietario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos del usuario.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("/auth/register/propietario")
    public ResponseEntity<GetUserDto> newPropietario(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                               @RequestPart("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(userService.savePropietario(newUser, file)));

    }

    @Operation(summary = "Método para obtener todos los usuarios dados de alta en la aplicación.", description = "Método para obtener todos los usuarios dados de alta en la aplicación.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha obtenido correctamente el listado de usuarios.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUserDto.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/users")
    public ResponseEntity<List<GetUserDto>> getAllUsers(@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(userEntity).stream().map(userDtoConverter::toGetUserDto).collect(java.util.stream.Collectors.toList()));
    }

    @Operation(summary = "Método para convertir a un usuario en administrador.", description = "Método para convertir a un usuario en administrador.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha convertido correctamente el usuario en administrador.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("/users/{username}/admin")
    public ResponseEntity<GetUserDto> convertToAdmin(@PathVariable String username,@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userDtoConverter.toGetUserDto(userService.convertToAdmin(userEntity,username)));
    }

    @Operation(summary = "Método para ver los datos de mi perfil.", description = "Método para ver los datos de mi perfil.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han obtenido correctamente los datos de mi perfil.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/me")
    public ResponseEntity<GetUserDto> getAuthenticatedUser(@AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.ok(userService.getAuthenticatedUser(userEntity));
    }

    @Operation(summary = "Método para obtener los datos del perfil de un usuario según su username.", description = "Método para obtener los datos del perfil de un usuario según su username.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han obtenido correctamente los datos del perfil del usuario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/profile/{username}")
    public ResponseEntity<GetUserDto> getUserProfileById(@PathVariable String username,@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfileByUsername(username, userEntity));
    }

    @Operation(summary = "Método para obtener los datos del perfil de un propietario", description = "Método para obtener los datos del perfil de un propietario", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han obtenido correctamente los datos del perfil del propietario.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/profile/propietario/{username}")
    public ResponseEntity<GetPropietarioDto> getPropietarioProfileById(@PathVariable String username, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPropietarioProfileByUsername(username, userEntity));
    }

    @Operation(summary = "Método para editar los datos de mi perfil.", description = "Método para editar los datos de mi perfil.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han editado correctamente los datos de mi perfil.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("/profile/me")
    public ResponseEntity<GetUserDto> editMyProfile (@Valid @RequestPart CreateUserDto newUser,
                                                     @RequestPart MultipartFile file,
                                                     @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.editMyProfile(newUser,file, userEntity));
    }

    @Operation(summary = "Método para comprobar si el nombre de usuario ya existe.", description = "Método para comprobar si el nombre de usuario ya existe.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "El nombre de usuario no existe.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/check/username/{username}")
    public ResponseEntity<UserNameAvailabilityDto> checkUsernameAvailability(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkUsernameAvailability(username));
    }


}
