package com.salesianostriana.dam.finalapi.dtos.site;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.persistence.Lob;
import javax.validation.constraints.*;
import java.time.LocalTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateSiteDto {

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
    @Size(min = 9, max = 9, message = "{site.phone.size}")
    private String phone;
    @URL(message = "{site.url.url}")
    private String web;
    @NotBlank(message = "{site.openingHour.blank}")
    @Min(value = 0, message = "{site.openingHour.min}")
    @Max(value = 23, message = "{site.openingHour.max}")
    private LocalTime openingHour;
    @NotBlank(message = "{site.closingHour.blank}")
    @Min(value = 0, message = "{site.closingHour.min}")
    @Max(value = 23, message = "{site.closingHour.max}")
    private LocalTime closingHour;
}
