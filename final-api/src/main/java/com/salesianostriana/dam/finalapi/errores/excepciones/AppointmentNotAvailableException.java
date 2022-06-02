package com.salesianostriana.dam.finalapi.errores.excepciones;

public class AppointmentNotAvailableException extends RuntimeException {

    public AppointmentNotAvailableException (String message) {
        super(message);
    }
}
