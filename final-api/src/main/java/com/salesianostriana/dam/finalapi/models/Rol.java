package com.salesianostriana.dam.finalapi.models;

public enum Rol {
    ADMIN("ADMIN"), CLIENTE("CLIENTE"), PROPIETARIO("PROPIETARIO"), ;

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

