package com.salesianostriana.dam.finalapi.dtos.site;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetListSiteDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String email;
    private String phone;
    private String postalCode;
    private long totalComments;
    private double rate;
    private String scaledFileUrl;
    private boolean liked;
    private String propietario;
}
