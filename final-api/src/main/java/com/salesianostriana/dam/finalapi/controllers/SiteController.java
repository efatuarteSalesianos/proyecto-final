package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.site.CreateSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import com.salesianostriana.dam.finalapi.dtos.site.SiteDtoConverter;
import com.salesianostriana.dam.finalapi.models.User;
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

    @GetMapping("/")
    public ResponseEntity<List<GetSiteDto>> getAllSites(){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllSites());
    }

    @GetMapping("/{type}")
    public ResponseEntity<List<GetSiteDto>> getAllSitesByType(@PathVariable String type){
        return ResponseEntity.status(HttpStatus.OK).body(siteService.getAllSitesByType(type));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetSiteDto> getSingleSite(@PathVariable Long id, @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.getSingleSite(id)));
    }

    @PostMapping("/")
    public ResponseEntity<GetSiteDto> createSite(@Valid @RequestPart("newSite") CreateSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.createSite(newSite, file, user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetSiteDto> createSite(@PathVariable Long id, @Valid @RequestPart("newSite") CreateSiteDto newSite,
                                                 @RequestPart("file") MultipartFile file,
                                                 @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(siteDtoConverter.toGetSiteDto(siteService.editSite(id, newSite, file)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable Long id, @AuthenticationPrincipal User user) {
        return siteService.deleteSite(id, user) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.badRequest().build();

    }

    @PostMapping("/like/{id}")
    public ResponseEntity<GetSiteDto> addLike (@PathVariable Long id, @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(siteDtoConverter.toGetSiteDto(siteService.addLike(id,user)));
    }

    @DeleteMapping("/like/{id}")
    public ResponseEntity<GetSiteDto> deleteLike (@PathVariable Long id, @AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(siteDtoConverter.toGetSiteDto(siteService.deleteLike(id,user)));
    }




}
