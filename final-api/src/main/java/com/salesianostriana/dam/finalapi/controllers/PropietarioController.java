package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.user.GetPropietarioDto;
import com.salesianostriana.dam.finalapi.dtos.user.UserDtoConverter;
import com.salesianostriana.dam.finalapi.models.Rol;
import com.salesianostriana.dam.finalapi.models.Site;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import com.salesianostriana.dam.finalapi.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log
@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
@Tag(name="Propietario", description = "Clase controladora de propietarios")
public class PropietarioController {

    private final UserService userEntityService;
    private final UserDtoConverter userDtoConverter;

    @Operation(summary = "Se muestran los datos de un propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se muestran los datos correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Site.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe el propietario que se busca",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetPropietarioDto> findPropietarioById(@AuthenticationPrincipal UserEntity user, @Parameter(description = "El id del propietario que se desea consultar") @PathVariable UUID id) {
        return ResponseEntity
                .of(userEntityService.findById(id)
                        .map(userDtoConverter::toGetPropietarioDto));
    }

    @Operation(summary = "Se borra un propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el propietario correctamente",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Site.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No existe el propietario que se busca",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePropietarioById(@AuthenticationPrincipal UserEntity user, @Parameter(description = "El id del propietario que se quiere eliminar") @PathVariable UUID id){
        userEntityService.deleteById(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}