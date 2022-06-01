package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.appointment.CreateAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.CreateCommentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.GetCommentDto;
import com.salesianostriana.dam.finalapi.dtos.site.CreateSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetListSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.SiteDtoConverter;
import com.salesianostriana.dam.finalapi.models.*;
import com.salesianostriana.dam.finalapi.repositories.SiteRepository;
import com.salesianostriana.dam.finalapi.services.SiteService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/site")
@Tag(name = "Site", description = "Clase controladora de los negocios")
public class SiteController {
    private final SiteService siteService;
    private final SiteDtoConverter siteDtoConverter;

    private final SiteRepository siteRepository;

    @Operation(summary = "Método para obtener un listado de todos los negocios que existen", description = "Método para obtener un listado de todos los negocios que existen", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene correctamente el listado de los negocios.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existen negocios.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/")
    public ResponseEntity<List<GetListSiteDto>> getAllSites(){
        List<GetListSiteDto> sites = siteService.getAllSites();
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "Método para obtener todos los negocios que contengan la cadena indicada como parámetro en el nombre", description = "Método para obtener todos los negocios que contengan la cadena indicada como parámetro en el nombre", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existen negocios.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping(value = "/", params = {"name"})
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByNameContaining(@RequestParam(defaultValue = "") String search){
        List<GetListSiteDto> sites = siteService.getSitesByName(search);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "Método para obtener todos los negocios que contengan la cadena indicada como parámetro en la ciudad", description = "Método para obtener todos los negocios que contengan la cadena indicada como parámetro en la ciudad", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existen negocios.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping(value = "/", params = {"city"})
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByCity(@RequestParam(defaultValue = "") String city){
        List<GetListSiteDto> sites = siteService.getAllSitesByCity(city);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "Método para obtener todos los negocios que contengan la cadena indicada como parámetro en el código postal", description = "Método para obtener todos los negocios que contengan la cadena indicada como parámetro en el código postal", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existen negocios.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping(value = "/", params = {"postalCode"})
    public ResponseEntity<List<GetListSiteDto>> getSitesByPostalCode(@RequestParam(defaultValue = "") String postalCode){
        List<GetListSiteDto> sites = siteService.getAllSitesByPostalCode(postalCode);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "Método para obtener todos los negocios que cuyo rating sea mayor que el indicado como parámetro", description = "Método para obtener todos los negocios que cuyo rating sea mayor que el indicado como parámetro", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existen negocios.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping(value = "/", params = {"rate"})
    public ResponseEntity<List<GetListSiteDto>> getSitesByRateGreaterThan(@RequestParam(defaultValue = "3.0") Double rate){
        List<GetListSiteDto> sites = siteRepository.findByRateGreaterThan(rate);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "Método para obtener todos los negocios que cuyo tipo sea el mismo que el indicado como parámetro", description = "Método para obtener todos los negocios que cuyo tipo sea el mismo que el indicado como parámetro", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No existen negocios.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping(value = "/", params = {"type"})
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByType(@PathVariable SiteTypes type){
        List<GetListSiteDto> sites = siteRepository.findByType(type);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "Método para obtener el listado de negocios cuyo propietario coincida con el parámetro", description = "Método para obtener el listado de negocios cuyo propietario coincida con el parámetro", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping(value = "/", params = {"propietarioId"})
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByPropietario(@PathVariable Long propietarioId){
        List<GetListSiteDto> sites = siteRepository.findByPropietarioId(propietarioId);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "Método para obtener la información de un negocio", description = "Método para obtener la información de un negocio según su id", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene la información de un negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetSiteDto> getSingleSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.getSingleSite(id)));
    }

    @Operation(summary = "Método para crear un negocio", description = "Método para crear un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se crea el negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<GetSiteDto> createSite(@Valid @RequestPart("newSite") CreateSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity.getRol().equals(Rol.ADMIN) || userEntity.getRol().equals(Rol.PROPIETARIO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.createSite(newSite, file)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Método para editar un negocio", description = "Método para editar un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se editan los datos del negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetSiteDto> editSite(@PathVariable Long id, @Valid @RequestPart("newSite") CreateSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity.getRol().equals(Rol.ADMIN) || userEntity.getRol().equals(Rol.PROPIETARIO)) {
            return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.editSite(id, newSite, file)));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Método para borrar un negocio", description = "Método para borrar un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity.getRol().equals(Rol.ADMIN) || userEntity.getRol().equals(Rol.PROPIETARIO)) {
            return siteService.deleteSite(id, userEntity) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Método para añadir un like a un negocio", description = "Método para añadir un like a un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se añade el like correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("/{siteId}/like")
    public ResponseEntity<GetSiteDto> addLike(@PathVariable Long siteId, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.addLike(siteId, userEntity)));
    }

    @Operation(summary = "Método para eliminar un like de un negocio", description = "Método para eliminar un like de un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el like correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("/{siteId}/like")
    public ResponseEntity<GetSiteDto> deleteLike(@PathVariable Long siteId, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(siteDtoConverter.toGetSiteDto(siteService.deleteLike(siteId, userEntity)));
    }

    @Operation(summary = "Método para obtener el listado de negocios favoritos del usuario logueado", description = "Método para obtener el listado de negocios favoritos del usuario logueado", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/favorites")
    public ResponseEntity<List<GetListSiteDto>> getFavorites(@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.ok(siteService.getLikedSites(userEntity));
    }

    @Operation(summary = "Método para añadir un comentario a un negocio", description = "Método para añadir un comentario a un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se añade el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("{id}/comment")
    public ResponseEntity<GetCommentDto> addComment(@PathVariable Long id, @Valid @RequestBody CreateCommentDto newComment, @AuthenticationPrincipal UserEntity userEntity, MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addComment(id, newComment, userEntity, file));
    }

    @Operation(summary = "Método para eliminar un comentario de un negocio", description = "Método para eliminar un comentario de un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/comment")
    public ResponseEntity<List<GetCommentDto>> getAllComments(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllComments(id));
    }

    @Operation(summary = "Método para obtener la información de un comentario", description = "Método para obtener la información de un comentario", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/comment/{commentId}")
    public ResponseEntity<GetCommentDto> getSingleComment(@PathVariable Long id, @PathVariable CommentPK commentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getComment(id, commentId));
    }

    @Operation(summary = "Método para editar la información de un comentario", description = "Método para editar la información de un comentario", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se edita la información del comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("{id}/comment/{commentId}")
    public ResponseEntity<GetCommentDto> editComment(@PathVariable Long id, @PathVariable CommentPK commentId, @Valid @RequestBody CreateCommentDto newComment, @AuthenticationPrincipal UserEntity userEntity, MultipartFile file){
        if(userEntity.getRol().equals(Rol.ADMIN) || userEntity.getComments().stream().anyMatch(comment -> comment.getId().equals(commentId))) {
            if (siteService.findById(id).isEmpty()) {
                return ResponseEntity
                        .notFound()
                        .build();
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(siteService.editComment(id, commentId, userEntity, file, newComment));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Método para eliminar un comentario", description = "Método para eliminar un comentario", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @PathVariable CommentPK commentId, @AuthenticationPrincipal UserEntity userEntity){
        if(userEntity.getRol().equals(Rol.ADMIN) || userEntity.getComments().stream().anyMatch(comment -> comment.getId().equals(commentId))) {
            if(siteService.findById(id).isEmpty()) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            else {
                siteService.deleteComment(id, userEntity.getId(), commentId);
                return ResponseEntity
                        .noContent()
                        .build();
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Método para añadir una cita a un negocio", description = "Método para añadir una cita a un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se añade la cita correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("{id}/appointment")
    public ResponseEntity<GetAppointmentDto> addAppointment(@PathVariable Long id, @Valid @RequestBody CreateAppointmentDto newAppointment, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addAppointment(id, userEntity.getId(), newAppointment));
    }

    @Operation(summary = "Método para listar las citas de un negocio", description = "Método para listar las citas de un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/appointment")
    public ResponseEntity<List<GetAppointmentDto>> getAllAppointments(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllAppointments(id));
    }

    @Operation(summary = "Método para obtener la información de una cita", description = "", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = ".",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<GetAppointmentDto> getSingleAppointment(@PathVariable Long id, @PathVariable AppointmentPK appointmentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAppointment(id, appointmentId));
    }

    @Operation(summary = "Método para editar la información de una cita", description = "Método para editar la información de una cita", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se actualizó correctamente la información.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<GetAppointmentDto> editAppointment(@PathVariable Long id, @PathVariable AppointmentPK appointmentId, @Valid @RequestBody CreateAppointmentDto newAppointment, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity.getRol().equals(Rol.ADMIN) || userEntity.getAppointments().stream().anyMatch(appointment -> appointment.getId().equals(appointmentId))) {
            if (siteService.findById(id).isEmpty()) {
                return ResponseEntity
                        .notFound()
                        .build();
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(siteService.editAppointment(id, appointmentId, userEntity, newAppointment));
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Método para eliminar la información de una cita", description = "Método para eliminar la información de una cita", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina correctamente la cita.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id, @PathVariable AppointmentPK appointmentId, @AuthenticationPrincipal UserEntity userEntity){
        if(userEntity.getRol().equals(Rol.ADMIN) || userEntity.getAppointments().stream().anyMatch(appointment -> appointment.getId().equals(appointmentId))) {
            if(siteService.findById(id).isEmpty()) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            else {
                siteService.deleteAppointment(id, userEntity, appointmentId);
                return ResponseEntity
                        .noContent()
                        .build();
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(summary = "Método para comprobar si una hora está libre de citas", description = "Método para comprobar si una hora está libre de citas", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La hora indicada está libre.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petición.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("{id}/appointment/check")
    public ResponseEntity<Boolean> checkAppointment(@PathVariable Long id, @Valid @RequestBody LocalDateTime appointmentTime){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.isAppointmentTimeAvailable(id, appointmentTime));
    }
}
