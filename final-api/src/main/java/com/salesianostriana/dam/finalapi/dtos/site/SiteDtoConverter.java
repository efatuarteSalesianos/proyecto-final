package com.salesianostriana.dam.finalapi.dtos.site;

import com.salesianostriana.dam.finalapi.models.Site;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SiteDtoConverter {
    public GetSiteDto toGetSiteDto(Site site) {
        return GetSiteDto.builder()
                .name(site.getName())
                .description(site.getDescription())
                .address(site.getAddress())
                .city(site.getCity())
                .postalCode(site.getPostalCode())
                .email(site.getEmail())
                .web(site.getWeb())
                .phone(site.getPhone())
//                .comments(site.getComments())
//                .total_comments((site.getComments().size()))
//                .rate(average_rate(site.getComments()))
                .originalFileUrl(site.getOriginalFile())
                .scaledFileUrl(site.getScaledFile())
                .likes(site.getLikes().size())
                .likes(site.getLikes().size())
                .build();
    }

//    public double average_rate(List<Comment> comments) {
//        double sum = 0.0;
//        for (Comment comment : comments) {
//            sum += comment.getRate();
//        }
//        if (comments.size() == 0)
//            return 0.0;
//        else
//            return sum / comments.size();
//    }
}
