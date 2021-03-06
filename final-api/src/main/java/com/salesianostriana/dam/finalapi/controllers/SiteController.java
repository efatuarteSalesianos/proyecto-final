package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.appointment.CreateAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.CreateCommentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.GetCommentDto;
import com.salesianostriana.dam.finalapi.dtos.site.*;
import com.salesianostriana.dam.finalapi.errores.excepciones.BadRequestException;
import com.salesianostriana.dam.finalapi.errores.excepciones.CantCommentWithoutAppointmentException;
import com.salesianostriana.dam.finalapi.models.AppointmentPK;
import com.salesianostriana.dam.finalapi.models.CommentPK;
import com.salesianostriana.dam.finalapi.models.SiteTypes;
import com.salesianostriana.dam.finalapi.models.UserEntity;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/site")
@Tag(name = "Site", description = "Clase controladora de los negocios")
public class SiteController {
    private final SiteService siteService;
    private final SiteDtoConverter siteDtoConverter;

    private final SiteRepository siteRepository;

    @Operation(summary = "M??todo para obtener un listado de todos los negocios que existen", description = "M??todo para obtener un listado de todos los negocios que existen", tags = "Site")
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

    @Operation(summary = "M??todo para obtener todos los negocios que contengan la cadena indicada como par??metro en el nombre", description = "M??todo para obtener todos los negocios que contengan la cadena indicada como par??metro en el nombre", tags = "Site")
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

    @Operation(summary = "M??todo para obtener todos los negocios que contengan la cadena indicada como par??metro en la ciudad", description = "M??todo para obtener todos los negocios que contengan la cadena indicada como par??metro en la ciudad", tags = "Site")
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

    @Operation(summary = "M??todo para obtener todos los negocios que contengan la cadena indicada como par??metro en el c??digo postal", description = "M??todo para obtener todos los negocios que contengan la cadena indicada como par??metro en el c??digo postal", tags = "Site")
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

    @Operation(summary = "M??todo para obtener todos los negocios que cuyo rating sea mayor que el indicado como par??metro", description = "M??todo para obtener todos los negocios que cuyo rating sea mayor que el indicado como par??metro", tags = "Site")
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

    @Operation(summary = "M??todo para obtener todos los negocios que cuyo tipo sea el mismo que el indicado como par??metro", description = "M??todo para obtener todos los negocios que cuyo tipo sea el mismo que el indicado como par??metro", tags = "Site")
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

    @Operation(summary = "M??todo para obtener el listado de negocios cuyo propietario coincida con el par??metro", description = "M??todo para obtener el listado de negocios cuyo propietario coincida con el par??metro", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping(value = "/", params = {"username"})
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByPropietario(@PathVariable String username){
        List<GetListSiteDto> sites = siteRepository.findByPropietarioUsername(username);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @Operation(summary = "M??todo para obtener la informaci??n de un negocio", description = "M??todo para obtener la informaci??n de un negocio seg??n su id", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene la informaci??n de un negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetSiteDto> getSingleSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.getSingleSite(id)));
    }

    @Operation(summary = "M??todo para crear un negocio", description = "M??todo para crear un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se crea el negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<GetSiteDto> createSite(@Valid @RequestPart("newSite") CreateSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.createSite(newSite, file)));
    }

    @Operation(summary = "M??todo para editar un negocio", description = "M??todo para editar un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se editan los datos del negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<GetSiteDto> editSite(@PathVariable Long id, @Valid @RequestPart("newSite") EditSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.editSite(id, newSite, file)));
    }

    @Operation(summary = "M??todo para borrar un negocio", description = "M??todo para borrar un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el negocio correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity) {
        return siteService.deleteSite(id, userEntity) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.badRequest().build();
    }

    @Operation(summary = "M??todo para a??adir un like a un negocio", description = "M??todo para a??adir un like a un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se a??ade el like correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("/{siteId}/like")
    public ResponseEntity<GetSiteDto> addLike(@PathVariable Long siteId, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addLike(siteId, userEntity));
    }

    @Operation(summary = "M??todo para eliminar un like de un negocio", description = "M??todo para eliminar un like de un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el like correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("/{siteId}/like")
    public ResponseEntity<?> deleteLike(@PathVariable Long siteId, @AuthenticationPrincipal UserEntity userEntity){
        siteService.deleteLike(siteId, userEntity);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "M??todo para obtener el listado de negocios favoritos del usuario logueado", description = "M??todo para obtener el listado de negocios favoritos del usuario logueado", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("/favourites")
    public ResponseEntity<List<GetListSiteDto>> getFavorites(@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.ok(siteService.getLikedSites(userEntity));
    }

    @Operation(summary = "M??todo para a??adir un comentario a un negocio", description = "M??todo para a??adir un comentario a un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se a??ade el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("{id}/comment")
    public ResponseEntity<GetCommentDto> addComment(@PathVariable Long id, @Valid @RequestPart("newComment") CreateCommentDto newComment, @AuthenticationPrincipal UserEntity userEntity, @RequestPart("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addComment(id, newComment, userEntity, file));
    }

    @Operation(summary = "M??todo para eliminar un comentario de un negocio", description = "M??todo para eliminar un comentario de un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/comment")
    public ResponseEntity<List<GetCommentDto>> getAllComments(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllComments(id));
    }

    @Operation(summary = "M??todo para obtener la informaci??n de un comentario", description = "M??todo para obtener la informaci??n de un comentario", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/comment/{commentId}")
    public ResponseEntity<GetCommentDto> getSingleComment(@PathVariable Long id, @PathVariable CommentPK commentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getComment(id, commentId));
    }

    @Operation(summary = "M??todo para editar la informaci??n de un comentario", description = "M??todo para editar la informaci??n de un comentario", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se edita la informaci??n del comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("{id}/comment/{commentId}")
    public ResponseEntity<GetCommentDto> editComment(@PathVariable Long id, @PathVariable CommentPK commentId, @Valid @RequestBody CreateCommentDto newComment, @AuthenticationPrincipal UserEntity userEntity, MultipartFile file){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.editComment(id, commentId, userEntity, file, newComment));
    }

    @Operation(summary = "M??todo para eliminar un comentario", description = "M??todo para eliminar un comentario", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina el comentario correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id, @PathVariable CommentPK commentId, @AuthenticationPrincipal UserEntity userEntity) throws BadRequestException {
        siteService.deleteComment(id, userEntity.getId(), commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "M??todo para a??adir una cita a un negocio", description = "M??todo para a??adir una cita a un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se a??ade la cita correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("{id}/appointment")
    public ResponseEntity<GetAppointmentDto> addAppointment(@PathVariable Long id, @Valid @RequestPart("newAppointment") CreateAppointmentDto newAppointment, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addAppointment(id, userEntity, newAppointment));
    }

    @Operation(summary = "M??todo para listar las citas de un negocio", description = "M??todo para listar las citas de un negocio", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se obtiene el listado correctamente.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/appointment")
    public ResponseEntity<List<GetAppointmentDto>> getAllAppointments(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllAppointments(id));
    }

    @Operation(summary = "M??todo para obtener la informaci??n de una cita", description = "M??todo para obtener la informaci??n de una cita", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = ".",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @GetMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<GetAppointmentDto> getSingleAppointment(@PathVariable Long id, @PathVariable AppointmentPK appointmentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAppointment(id, appointmentId));
    }

    @Operation(summary = "M??todo para editar la informaci??n de una cita", description = "M??todo para editar la informaci??n de una cita", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se actualiz?? correctamente la informaci??n.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PutMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<GetAppointmentDto> editAppointment(@PathVariable Long id, @PathVariable AppointmentPK appointmentId, @Valid @RequestBody CreateAppointmentDto newAppointment, @AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.OK).body(siteService.editAppointment(id, appointmentId, userEntity, newAppointment));
    }

    @Operation(summary = "M??todo para eliminar la informaci??n de una cita", description = "M??todo para eliminar la informaci??n de una cita", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se elimina correctamente la cita.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @DeleteMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<?> deleteAppointment(@PathVariable Long id, @PathVariable AppointmentPK appointmentId, @AuthenticationPrincipal UserEntity userEntity){
            siteService.deleteAppointment(id, userEntity, appointmentId);
            return ResponseEntity
                    .noContent()
                    .build();
    }

    @Operation(summary = "M??todo para comprobar si una hora est?? libre de citas", description = "M??todo para comprobar si una hora est?? libre de citas", tags = "Site")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "La hora indicada est?? libre.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = "400",
                    description = "Hay un error en los datos de la petici??n.",
                    content = @Content),
            @ApiResponse(responseCode = "403",
                    description = "Acceso denegado.",
                    content = @Content)
    })
    @PostMapping("{id}/appointment/check")
    public ResponseEntity<Boolean> checkAppointment(@PathVariable Long id, @Valid @RequestBody String appointmentTime){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.isAppointmentTimeAvailable(id, appointmentTime));
    }
}
