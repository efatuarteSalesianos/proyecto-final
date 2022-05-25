package com.salesianostriana.dam.finalapi.dtos.user;

import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetPropietarioDto {

    private UUID id;
    private String fullName, phone, email, avatar;
    private int num_negocios;
    @Builder.Default
    private List<GetSiteDto> negocios = new ArrayList<>();
}
