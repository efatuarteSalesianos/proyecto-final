package com.salesianostriana.dam.finalapi.dtos.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.salesianostriana.dam.finalapi.validation.simple.anotations.FutureDate;
import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

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
