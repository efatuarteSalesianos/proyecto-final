package com.salesianostriana.dam.finalapi.dtos.comment;

import com.salesianostriana.dam.finalapi.models.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentDtoConverter {

    public GetCommentDto toGetCommentDto(Comment comment) {
        return GetCommentDto.builder()
                .cliente(comment.getCliente().getFull_name())
                .site(comment.getSite().getName())
                .title(comment.getTitle())
                .description(comment.getDescription())
                .build();
    }
}
