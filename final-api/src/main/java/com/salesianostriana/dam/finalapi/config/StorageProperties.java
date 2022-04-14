package com.salesianostriana.dam.finalapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class StorageProperties {
    @Value("${storage.location}")
    private String location;
}
