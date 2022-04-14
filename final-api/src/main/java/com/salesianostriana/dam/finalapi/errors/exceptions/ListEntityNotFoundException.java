package com.salesianostriana.dam.finalapi.errors.exceptions;

public class ListEntityNotFoundException extends EntityNotFoundException{
    public ListEntityNotFoundException(Class clazz) {
        super(String.format("No %s entity type was found", clazz.getSimpleName()));
    }
}