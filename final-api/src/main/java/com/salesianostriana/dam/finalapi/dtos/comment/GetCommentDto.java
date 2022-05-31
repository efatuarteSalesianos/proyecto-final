package com.salesianostriana.dam.finalapi.dtos.comment;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetCommentDto {

    private String cliente;
    private String site;
    private String title;
    private String description;
    private double rate;
    private String image;
}
