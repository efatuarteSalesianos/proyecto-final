package com.salesianostriana.dam.finalapi.users.models;

public enum UsersRol {
    USER("USER");

    private String value;

    private UsersRol(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
