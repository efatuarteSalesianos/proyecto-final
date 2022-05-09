package com.salesianostriana.dam.finalapi.controllers;

import com.salesianostriana.dam.finalapi.dtos.user.CreateUserDto;
import com.salesianostriana.dam.finalapi.dtos.user.GetUserDto;
import com.salesianostriana.dam.finalapi.dtos.user.UserDtoConverter;
import com.salesianostriana.dam.finalapi.dtos.user.UserNameAvailabilityDto;
import com.salesianostriana.dam.finalapi.models.UserEntity;
import com.salesianostriana.dam.finalapi.services.UserService;
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

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> newUser(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                              @RequestPart("file") MultipartFile file) {
        if(!file.isEmpty() || newUser!=null)
            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(userService.saveUser(newUser, file)));
        else
            throw new MultipartException("Te provided multipart file must not be null");
    }

    @PostMapping("/auth/register/admin")
    public ResponseEntity<GetUserDto> newAdmin(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                               @RequestPart("file") MultipartFile file) {

            return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(userService.saveAdmin(newUser, file)));

    }

    @PostMapping("/auth/register/propietario")
    public ResponseEntity<GetUserDto> newPropietario(@Valid @RequestPart("newUser") CreateUserDto newUser,
                                               @RequestPart("file") MultipartFile file) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.toGetUserDto(userService.savePropietario(newUser, file)));

    }

    @GetMapping("/users")
    public ResponseEntity<List<GetUserDto>> getAllUsers(@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers(userEntity).stream().map(userDtoConverter::toGetUserDto).collect(java.util.stream.Collectors.toList()));
    }

    @PutMapping("/users/{username}/admin")
    public ResponseEntity<GetUserDto> convertToAdmin(@PathVariable String username,@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userDtoConverter.toGetUserDto(userService.convertToAdmin(userEntity,username)));
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserDto> getAuthenticatedUser(@AuthenticationPrincipal UserEntity userEntity) {
        return ResponseEntity.ok(userService.getAuthenticatedUser(userEntity));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<GetUserDto> getUserProfileById(@PathVariable String username,@AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserProfileByUsername(username, userEntity));
    }

    @PutMapping("/profile/me")
    public ResponseEntity<GetUserDto> editMyProfile (@Valid @RequestPart CreateUserDto newUser,
                                                     @RequestPart MultipartFile file,
                                                     @AuthenticationPrincipal UserEntity userEntity){
        return ResponseEntity.status(HttpStatus.OK).body(userService.editMyProfile(newUser,file, userEntity));
    }

    @GetMapping("/usernameavailable/{username}")
    public ResponseEntity<UserNameAvailabilityDto> checkUsernameAvailability(@PathVariable String username){
        return ResponseEntity.status(HttpStatus.OK).body(userService.checkUsernameAvailability(username));
    }


}
