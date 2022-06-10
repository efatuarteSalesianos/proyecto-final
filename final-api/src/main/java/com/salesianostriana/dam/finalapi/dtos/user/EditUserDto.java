package com.salesianostriana.dam.finalapi.dtos.user;

import com.salesianostriana.dam.finalapi.validation.simple.anotations.UniqueUsername;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditUserDto {
    private String fullName;

    @NotBlank(message = "{userEntity.email.blank}")
    @Email(message = "{userEntity.email.email}")
    private String email;

    @Past(message = "{userEntity.birthday.past}")
    private Date birthDate;

    @NotBlank(message = "{userEntity.phone.blank}")
    @Size(min = 9, max = 9, message = "{userEntity.phone.size}")
    private String phone;
}
