package com.salesianostriana.dam.finalapi.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetUserDto {
    private String full_name;
    private String username;
    private String email;
    private String avatar;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    private String phone;
    private String rol;
    private int followers;
}
