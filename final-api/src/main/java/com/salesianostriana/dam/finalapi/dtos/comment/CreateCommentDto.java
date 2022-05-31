package com.salesianostriana.dam.finalapi.dtos.comment;

import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateCommentDto {

    @NotBlank(message = "{comment.title.blank}")
    private String title;

    @NotBlank(message = "{comment.description.blank}")
    @Lob
    private String description;

    @NotBlank(message = "{comment.rate.blank}")
    @Min(value = 0, message = "{comment.rate.min}")
    @Max(value = 5, message = "{comment.rate.max}")
    private double rate;

    private String image;
}
