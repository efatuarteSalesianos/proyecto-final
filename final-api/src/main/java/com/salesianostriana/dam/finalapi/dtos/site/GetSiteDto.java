package com.salesianostriana.dam.finalapi.dtos.site;

import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetSiteDto {
    private Long id;
    private String name;
    private String description;
    private String address;
    private String city;
    private String postalCode;
    private String email;
    private String web;
    private String phone;
//    private List<Comment> comments;
    private int total_comments;
    private double rate;
    private String OriginalFileUrl;
    private String scaledFileUrl;
    private int likes;
    private boolean liked;
}
