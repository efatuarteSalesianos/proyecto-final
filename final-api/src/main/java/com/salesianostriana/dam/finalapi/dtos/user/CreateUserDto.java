package com.salesianostriana.dam.finalapi.dtos.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.finalapi.validation.multiple.anotations.FieldsValueMatch;
import com.salesianostriana.dam.finalapi.validation.simple.anotations.UniqueUsername;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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

    @NotBlank(message = "{user.full_name.blank}")
    private String full_name;
    @UniqueUsername(message = "{user.username.unique}")
    @NotBlank(message = "{user.username.blank}")
    private String username;
    @NotBlank(message = "{user.email.blank}")
    @Email(message = "{user.email.email}")
    private String email;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotBlank(message = "{user.birthDate.blank}")
    private Date birthDate;
    @NotBlank(message = "{user.phone.blank}")
    @Size(min = 9, max = 9, message = "{user.phone.size}")
    private String phone;
    @NotBlank(message = "{user.password.blank}")
    private String password;
    private String password2;
}