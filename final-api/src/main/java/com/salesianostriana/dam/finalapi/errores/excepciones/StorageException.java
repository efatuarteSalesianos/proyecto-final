package com.salesianostriana.dam.finalapi.errores.excepciones;

public class StorageException extends RuntimeException {
    public StorageException(String message, Exception e) {
        super(message,e);
    }
    public StorageException(String message) {
        super(message);
    }
}
