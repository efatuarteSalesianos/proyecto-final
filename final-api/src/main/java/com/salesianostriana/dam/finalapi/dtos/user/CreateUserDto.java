package com.salesianostriana.dam.finalapi.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.finalapi.validation.username.UniqueUsername;
import lombok.*;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateUserDto {
    @UniqueUsername(message = "{user.username.unique}")
    private String username;
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    private String phone;
    private boolean privateAccount;
    private String password;
    private String password2;
}