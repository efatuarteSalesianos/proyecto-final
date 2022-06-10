package com.salesianostriana.dam.finalapi.dtos.site;

import com.salesianostriana.dam.finalapi.models.SiteTypes;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.UUID;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditSiteDto {

    @NotBlank(message = "{site.name.blank}")
    @Size(min = 2, max = 32, message = "{site.name.size}")
    private String name;
    @Lob
    private String description;
    @NotBlank(message = "{site.address.blank}")
    private String address;
    @NotBlank(message = "{site.city.blank}")
    private String city;
    @NotBlank(message = "{site.postalCode.blank}")
    @Size(min = 5, max = 5, message = "{site.postalCode.size}")
    private String postalCode;
    @NotBlank(message = "{site.email.blank}")
    @Email(message = "{site.email.email}")
    private String email;
    @NotBlank(message = "{site.phone.blank}")
    @Size(min = 12, max = 12, message = "{site.phone.size}")
    private String phone;
    @URL(message = "{site.url.url}")
    private String web;
    private LocalTime openingHour;
    private LocalTime closingHour;
    private SiteTypes type;
    private UUID propietarioId;
}
