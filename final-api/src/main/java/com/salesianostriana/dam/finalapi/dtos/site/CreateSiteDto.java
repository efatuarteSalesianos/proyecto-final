package com.salesianostriana.dam.finalapi.dtos.site;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateSiteDto {
    private String title;
    private String description;
    private String direction;
    private String city;
    private String postalCode;
    private String email;
    private String phone;
    private String web;
}
