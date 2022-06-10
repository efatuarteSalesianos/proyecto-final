package com.salesianostriana.dam.finalapi.controllers;

import com.amazonaws.Response;
import com.salesianostriana.dam.finalapi.dtos.user.*;
import com.salesianostriana.dam.finalapi.models.Rol;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

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
    @PostMapping(value = "/auth/register")
    public ResponseEntity<GetUserDto> newUser(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                              @RequestPart("file") MultipartFile file) {
        UserEntity saved = userService.saveUser(newUser, file);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(saved));
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

        UserEntity saved = userService.saveAdmin(newUser, file);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(saved));
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
    public ResponseEntity<GetPropietarioDto> newPropietario(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                               @RequestPart("file") MultipartFile file) {

        UserEntity saved = userService.savePropietario(newUser, file);

        if(saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetPropietarioDto(saved));
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
        List<GetUserDto> users = userService.getAllUsers(userEntity);
        if (users.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(users);
    }

    @Operation(summary = "Método para obtener todos los propietarios dados de alta en la aplicación.", description = "Método para obtener todos los usuarios dados de alta en la aplicación.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha obtenido correctamente el listado de propietarios.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetUserDto.class))}),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/users/propietarios")
    public ResponseEntity<List<GetPropietarioDto>> getAllPropietarios(@AuthenticationPrincipal UserEntity userEntity){
        List<GetPropietarioDto> propietarios = userService.findByRol(Rol.PROPIETARIO);
        if (propietarios.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok(propietarios);
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
    public ResponseEntity<GetUserDto> convertToAdmin(@PathVariable String username, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.convertToAdmin(userEntity,username));
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
    @GetMapping("/profile/me")
    public ResponseEntity<GetUserDto> getMyProfile(@AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.ok(userDtoConverter.toGetUserDto(userEntity));
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
    public ResponseEntity<GetUserDto> getUserProfileByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfileByUsername(username));
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
    public ResponseEntity<GetPropietarioDto> getPropietarioProfileByUsername(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getPropietarioProfileByUsername(username));
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
    @PutMapping("/me")
    public ResponseEntity<GetUserDto> editMyProfile (@Valid @RequestPart EditUserDto newUser,
                                                     @RequestPart MultipartFile file,
                                                     @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.editMyProfile(newUser, file, userEntity));
    }

    @Operation(summary = "Método para borrar un usuario por su id.", description = "Método para borrar un usuario por su id.", tags = "Usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se han eliminado correctamente los datos del usuario seleccionado.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserEntity.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existe el usuario que se busca.",
                    content = @Content)
    })
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
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
