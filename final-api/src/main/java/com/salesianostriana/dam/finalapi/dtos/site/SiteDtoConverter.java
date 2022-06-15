package com.salesianostriana.dam.finalapi.dtos.site;

import com.salesianostriana.dam.finalapi.dtos.comment.CommentDtoConverter;
import com.salesianostriana.dam.finalapi.models.Comment;
import com.salesianostriana.dam.finalapi.models.Site;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SiteDtoConverter {

    private final CommentDtoConverter commentDtoConverter;
    public GetSiteDto toGetSiteDto(Site site) {
        return GetSiteDto.builder()
                .id(site.getId())
                .name(site.getName())
                .description(site.getDescription())
                .address(site.getAddress())
                .city(site.getCity())
                .postalCode(site.getPostalCode())
                .email(site.getEmail())
                .web(site.getWeb())
                .phone(site.getPhone())
                .comments(site.getComments().stream().map(commentDtoConverter::toGetCommentDto).collect(Collectors.toList()))
                .totalComments((site.getComments().size()))
                .rate(average_rate(site.getComments()))
                .originalFileUrl(site.getOriginalFile())
                .scaledFileUrl(site.getScaledFile())
                .likes(site.getLikes().size())
                .liked(site.isLiked())
                .type(site.getType().getValue())
                .propietario(site.getPropietario().getFullName())
                .build();
    }

    public GetListSiteDto toGetListSiteDto(Site site) {
        return GetListSiteDto.builder()
                .id(site.getId())
                .name(site.getName())
                .description(site.getDescription())
                .address(site.getAddress())
                .city(site.getCity())
                .email(site.getEmail())
                .phone(site.getPhone())
                .postalCode(site.getPostalCode())
                .totalComments((site.getComments().size()))
                .rate(average_rate(site.getComments()))
                .scaledFileUrl(site.getScaledFile())
                .liked(site.isLiked())
                .propietario(site.getPropietario().getFullName())
                .build();
    }

    public double average_rate(List<Comment> comments) {
        double sum = 0.0;
        for (Comment comment : comments) {
            sum += comment.getRate();
        }
        if (comments.size() == 0)
            return 0.0;
        else
            return sum / comments.size();
    }
}
