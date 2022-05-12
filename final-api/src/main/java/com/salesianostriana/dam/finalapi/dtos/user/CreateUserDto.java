package com.salesianostriana.dam.finalapi.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.finalapi.validation.multiple.anotations.FieldsValueMatch;
import com.salesianostriana.dam.finalapi.validation.simple.anotations.UniqueUsername;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "password",
                fieldMatch = "verifyPassword",
                message = "Las contraseñas no coinciden"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "verifyEmail",
                message = "Las direcciones de correo electrónico no coinciden"
        )
})
public class CreateUserDto {

    @NotBlank(message = "{userEntity.full_name.blank}")
    private String full_name;

    @UniqueUsername(message = "{userEntity.username.unique}")
    @NotBlank(message = "{userEntity.username.blank}")
    private String username;

    @NotBlank(message = "{userEntity.email.blank}")
    @Email(message = "{userEntity.email.email}")
    private String email;

    @Past(message = "{userEntity.birthday.past}")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotBlank(message = "{userEntity.birthDate.blank}")
    private Date birthDate;

    @NotBlank(message = "{userEntity.phone.blank}")
    @Size(min = 9, max = 9, message = "{userEntity.phone.size}")
    private String phone;

    @NotBlank(message = "{userEntity.password.blank}")
    private String password;
    private String password2;
}