package com.salesianostriana.dam.finalapi.errores.excepciones;

public class BadRequestException extends Exception {

    public BadRequestException(String msg) {
        super(msg);
    }
}
