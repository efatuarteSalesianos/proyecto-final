package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.appointment.CreateAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.appointment.GetAppointmentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.CreateCommentDto;
import com.salesianostriana.dam.finalapi.dtos.comment.GetCommentDto;
import com.salesianostriana.dam.finalapi.dtos.site.CreateSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.SiteDtoConverter;
import com.salesianostriana.dam.finalapi.models.AppointmentPK;
import com.salesianostriana.dam.finalapi.models.CommentPK;
import com.salesianostriana.dam.finalapi.models.Rol;
import com.salesianostriana.dam.finalapi.models.UserEntity;
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
    public ResponseEntity<List<GetSiteDto>> getAllSites(){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllSites());
    }

    @GetMapping(value = "/", params = {"name"})
    public ResponseEntity<List<GetSiteDto>> getAllSitesByNameContaining(@RequestParam(defaultValue = "") String search){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getSitesByName(search));
    }

    @GetMapping(value = "/", params = {"city"})
    public ResponseEntity<List<GetSiteDto>> getAllSitesByCity(@RequestParam(defaultValue = "") String city){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllSitesByCity(city));
    }

    @GetMapping(value = "/", params = {"postalCode"})
    public ResponseEntity<List<GetSiteDto>> getSitesByPostalCode(@RequestParam(defaultValue = "") String postalCode){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllSitesByPostalCode(postalCode));
    }

    @GetMapping(value = "/", params = {"rate"})
    public ResponseEntity<List<GetSiteDto>> getSitesByRateGreaterThan(@RequestParam(defaultValue = "3.0") Double rate){
        return ResponseEntity.status(HttpStatus.OK).body(siteRepository.findByRateGreaterThan(rate));
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<GetSiteDto>> getAllSitesByType(@PathVariable String type){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllSitesByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSiteDto> getSingleSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.getSingleSite(id)));
    }

    @PostMapping("/")
    public ResponseEntity<GetSiteDto> createSite(@Valid @RequestPart("newSite") CreateSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.createSite(newSite, file, userEntity)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetSiteDto> createSite(@PathVariable Long id, @Valid @RequestPart("newSite") CreateSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.editSite(id, newSite, file)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSite(@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity) {
        return siteService.deleteSite(id, userEntity) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/like/{id}")
    public ResponseEntity<GetSiteDto> addLike (@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.addLike(id, userEntity)));
    }

    @DeleteMapping("/like/{id}")
    public ResponseEntity<GetSiteDto> deleteLike (@PathVariable Long id, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(siteDtoConverter.toGetSiteDto(siteService.deleteLike(id, userEntity)));
    }

    //add comment
    @PostMapping("{id}/comment/")
    public ResponseEntity<GetCommentDto> addComment (@PathVariable Long id, @Valid @RequestBody CreateCommentDto newComment, @AuthenticationPrincipal UserEntity userEntity, MultipartFile file){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addComment(id, newComment, userEntity, file));
    }

    //list all comments
    @GetMapping("{id}/comment/")
    public ResponseEntity<List<GetCommentDto>> getAllComments (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllComments(id));
    }

    //show a single comment by site id and comment id
    @GetMapping("{id}/comment/{commentId}")
    public ResponseEntity<GetCommentDto> getSingleComment (@PathVariable Long id, @PathVariable CommentPK commentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getComment(id, commentId));
    }

    //edit a single comment by site id and comment id
    @PutMapping("{id}/comment/{commentId}")
    public ResponseEntity<GetCommentDto> editComment (@PathVariable Long id, @PathVariable CommentPK commentId, @Valid @RequestBody CreateCommentDto newComment, @AuthenticationPrincipal UserEntity userEntity, MultipartFile file){
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
    public ResponseEntity<?> deleteComment (@PathVariable Long id, @PathVariable CommentPK commentId, @AuthenticationPrincipal UserEntity userEntity){
        if(userEntity.getRol().equals(Rol.ADMIN) || userEntity.getComments().stream().anyMatch(comment -> comment.getId().equals(commentId))) {
            if(siteService.findById(id).isEmpty()) {
                return ResponseEntity
                        .notFound()
                        .build();
            }
            else {
                siteService.deleteComment(id, userEntity, commentId);
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
    public ResponseEntity<GetAppointmentDto> addAppointment (@PathVariable Long id, @Valid @RequestBody CreateAppointmentDto newAppointment, @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteService.addAppointment(id, userEntity, newAppointment));
    }

    //list all appointments by site id
    @GetMapping("{id}/appointment/")
    public ResponseEntity<List<GetAppointmentDto>> getAllAppointments (@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllAppointments(id));
    }

    //show a single appointment by site id and appointment id
    @GetMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<GetAppointmentDto> getSingleAppointment (@PathVariable Long id, @PathVariable AppointmentPK appointmentId){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAppointment(id, appointmentId));
    }

    //edit a single appointment by site id and appointment id
    @PutMapping("{id}/appointment/{appointmentId}")
    public ResponseEntity<GetAppointmentDto> editAppointment (@PathVariable Long id, @PathVariable AppointmentPK appointmentId, @Valid @RequestBody CreateAppointmentDto newAppointment, @AuthenticationPrincipal UserEntity userEntity) {
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
    public ResponseEntity<?> deleteAppointment (@PathVariable Long id, @PathVariable AppointmentPK appointmentId, @AuthenticationPrincipal UserEntity userEntity){
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
    public ResponseEntity<Boolean> checkAppointment (@PathVariable Long id, @Valid @RequestBody CreateAppointmentDto newAppointment){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.isAppointmentTimeAvailable(id, newAppointment));
    }
}
