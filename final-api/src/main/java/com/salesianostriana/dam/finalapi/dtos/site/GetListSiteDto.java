package com.salesianostriana.dam.finalapi.dtos.site;

import com.salesianostriana.dam.finalapi.dtos.comment.GetCommentDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetListSiteDto {
    private Long id;
    private String name;
    private String address;
    private String city;
    private String postalCode;
    private long totalComments;
    private double rate;
    private String scaledFileUrl;
    private boolean liked;
}
