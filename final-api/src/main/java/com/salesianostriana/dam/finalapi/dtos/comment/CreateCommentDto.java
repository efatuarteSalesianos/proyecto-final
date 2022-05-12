package com.salesianostriana.dam.finalapi.dtos.comment;

import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateCommentDto {

    @NotBlank(message = "{comment.title.notBlank}")
    private String title;

    @NotBlank(message = "{comment.description.notBlank}")
    @Lob
    private String description;

    @NotBlank(message = "{comment.rate.notBlank}")
    private int rate;

    private String image;
}
