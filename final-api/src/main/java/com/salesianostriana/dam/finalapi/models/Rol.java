package com.salesianostriana.dam.finalapi.models;

public enum Rol {
    ADMIN("Admin"), CLIENTE("Cliente"), PROPIETARIO("Propietario"), ;

    private String value;

    private Rol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

