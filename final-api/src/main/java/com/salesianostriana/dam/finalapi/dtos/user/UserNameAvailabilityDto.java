package com.salesianostriana.dam.finalapi.dtos.user;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserNameAvailabilityDto {
    private String providedUsername;
    private boolean available;
}
