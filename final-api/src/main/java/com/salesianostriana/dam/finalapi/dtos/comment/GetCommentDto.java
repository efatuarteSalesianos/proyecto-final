package com.salesianostriana.dam.finalapi.dtos.comment;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetCommentDto {

    private String cliente;
    private String site;
    private String title;
    private String description;
    private String image;
}
