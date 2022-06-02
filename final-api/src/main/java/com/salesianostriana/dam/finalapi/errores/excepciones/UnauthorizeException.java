package com.salesianostriana.dam.finalapi.errores.excepciones;

public class UnauthorizeException extends RuntimeException {
    public UnauthorizeException(String message) {
        super(message);
    }
    public UnauthorizeException(String message, Exception e) {
        super(message, e);
    }

}
