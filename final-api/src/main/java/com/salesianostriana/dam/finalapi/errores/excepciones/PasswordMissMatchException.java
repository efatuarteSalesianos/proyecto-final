package com.salesianostriana.dam.finalapi.errores.excepciones;

public class PasswordMissMatchException extends RuntimeException{
    public PasswordMissMatchException(String msg) {
        super(msg);
    }
    public PasswordMissMatchException() {
        super("Passwords don't match");
    }
}
