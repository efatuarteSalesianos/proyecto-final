package com.salesianostriana.dam.finalapi.dtos.site;

import com.salesianostriana.dam.finalapi.dtos.appointment.GetCommentDto;
import com.salesianostriana.dam.finalapi.models.Comment;
import lombok.*;

import java.util.List;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetSiteDto {
    private String name;
    private String description;
    private String address;
    private String city;
    private String postalCode;
    private String email;
    private String web;
    private String phone;
    private List<GetCommentDto> comments;
    private int total_comments;
    private double rate;
    private String originalFileUrl;
    private String scaledFileUrl;
    private int likes;
    private boolean liked;
}
