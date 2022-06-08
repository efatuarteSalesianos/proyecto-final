package com.salesianostriana.dam.finalapi.errores.excepciones;


public class SingleEntityNotFoundException extends EntityNotFoundException {

    public SingleEntityNotFoundException(String id, Class clazz) {
        super(String.format("No se pudo encontrar una entidad del tipo %s con ID: %s", clazz.getName(), id));
    }
}
