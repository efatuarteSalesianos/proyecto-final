package com.salesianostriana.dam.finalapi.errores.excepciones;

public class FileNotSupportedException extends RuntimeException {
    public FileNotSupportedException(String message) {
        super(message);
    }
    public FileNotSupportedException(String message, Exception e) {
        super(message, e);
    }
}
