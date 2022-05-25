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
public class SiteController {
    private final SiteService siteService;
    private final SiteDtoConverter siteDtoConverter;

    private final SiteRepository siteRepository;

    @GetMapping("/")
    public ResponseEntity<List<GetListSiteDto>> getAllSites(){
        List<GetListSiteDto> sites = siteService.getAllSites();
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @GetMapping(value = "/", params = {"name"})
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByNameContaining(@RequestParam(defaultValue = "") String search){
        List<GetListSiteDto> sites = siteService.getSitesByName(search);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @GetMapping(value = "/", params = {"city"})
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByCity(@RequestParam(defaultValue = "") String city){
        List<GetListSiteDto> sites = siteService.getAllSitesByCity(city);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @GetMapping(value = "/", params = {"postalCode"})
    public ResponseEntity<List<GetListSiteDto>> getSitesByPostalCode(@RequestParam(defaultValue = "") String postalCode){
        List<GetListSiteDto> sites = siteService.getAllSitesByPostalCode(postalCode);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @GetMapping(value = "/", params = {"rate"})
    public ResponseEntity<List<GetListSiteDto>> getSitesByRateGreaterThan(@RequestParam(defaultValue = "3.0") Double rate){
        List<GetListSiteDto> sites = siteRepository.findByRateGreaterThan(rate);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByType(@PathVariable SiteTypes type){
        List<GetListSiteDto> sites = siteRepository.findByType(type);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/{propietarioId}")
    public ResponseEntity<List<GetListSiteDto>> getAllSitesByPropietario(@PathVariable Long propietarioId){
        List<GetListSiteDto> sites = siteRepository.findByPropietarioId(propietarioId);
        if (sites.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(sites);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSiteDto> getSingleSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.getSingleSite(id)));
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity) {
        if (userEntity.getRol().equals(Rol.ADMIN) || userEntity.getRol().equals(Rol.PROPIETARIO)) {
            return siteService.deleteSite(id, userEntity) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<GetSiteDto> addLike(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.addLike(id, userEntity)));
    }

    @DeleteMapping("/like/{id}")
    public ResponseEntity<GetSiteDto> deleteLike(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(siteDtoConverter.toGetSiteDto(siteService.deleteLike(id, userEntity)));
    }

    //add comment
    @PostMapping("{id}/comment/")
    public ResponseEntity<GetCommentDto> addComment(@PathVariable Long id, @Valid @RequestBody CreateCommentDto newComment, @AuthenticationPrincipal UserEntity userEntity, MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addComment(id, newComment, userEntity, file));
    }

    //list all comments
    @GetMapping("{id}/comment/")
    public ResponseEntity<List<GetCommentDto>> getAllComments(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllComments(id));
    }

    //show a single comment by site id and comment id
    @GetMapping("{id}/comment/{commentId}")
    public ResponseEntity<GetCommentDto> getSingleComment(@PathVariable Long id, @PathVariable CommentPK commentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getComment(id, commentId));
    }

    //edit a single comment by site id and comment id
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

    //delete a single comment by site id and comment id
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

    //add appointment to site by site id
    @PostMapping("{id}/appointment/")
    public ResponseEntity<GetAppointmentDto> addAppointment(@PathVariable Long id, @Valid @RequestBody CreateAppointmentDto newAppointment, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addAppointment(id, userEntity.getId(), newAppointment));
    }

    //list all appointments by site id
    @GetMapping("{id}/appointment/")
    public ResponseEntity<List<GetAppointmentDto>> getAllAppointments(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllAppointments(id));
    }

    //show a single appointment by site id and appointment id
    @GetMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<GetAppointmentDto> getSingleAppointment(@PathVariable Long id, @PathVariable AppointmentPK appointmentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAppointment(id, appointmentId));
    }

    //edit a single appointment by site id and appointment id
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

    //delete a single appointment by site id and appointment id
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

    //check if appointment time is available
    @PostMapping("{id}/appointment/check")
    public ResponseEntity<Boolean> checkAppointment(@PathVariable Long id, @Valid @RequestBody LocalDateTime appointmentTime){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.isAppointmentTimeAvailable(id, appointmentTime));
    }
}
