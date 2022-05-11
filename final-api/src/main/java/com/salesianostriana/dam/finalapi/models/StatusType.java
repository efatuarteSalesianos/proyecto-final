package com.salesianostriana.dam.finalapi.models;

public enum StatusType {

    ESPERA("EN ESPERA"),
    ACEPTADA("ACEPTADA"),
    CANCELADA("CANCELADA");

    private String value;

    StatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
