package com.salesianostriana.dam.finalapi.errores.excepciones;


public class ListEntityNotFoundException extends EntityNotFoundException {

    public ListEntityNotFoundException(Class clazz) {
        super(String.format("No se pudieron encontrar elementos del tipo %s ", clazz.getName()));
    }
}
