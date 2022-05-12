package com.salesianostriana.dam.finalapi.errors.exceptions;

public class SingleEntityNotFoundException extends EntityNotFoundException{

    public SingleEntityNotFoundException(Long id, Class clazz) {
        super(String.format("Unable to find %s entity with id: %s ",clazz.getSimpleName(), id ));
    }
}