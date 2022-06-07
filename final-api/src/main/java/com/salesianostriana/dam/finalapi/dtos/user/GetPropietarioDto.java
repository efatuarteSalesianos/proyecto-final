package com.salesianostriana.dam.finalapi.dtos.user;

import com.salesianostriana.dam.finalapi.dtos.site.GetSiteDto;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor @NoArgsConstructor
@Getter @Setter
@Builder
public class GetPropietarioDto {

    private UUID id;
    private String fullName, username, phone, email, avatar;
    private Date birthDate;
    private int num_negocios;
    @Builder.Default
    private List<GetSiteDto> negocios = new ArrayList<>();
}
