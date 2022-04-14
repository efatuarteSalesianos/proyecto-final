package com.salesianostriana.dam.finalapi.models;

public enum SiteTypes {
    PELUQUERIA("PELUQUERIA"), BARBERIA("BARBERIA"), CENTRO_ESTETICA("CENTRO_ESTETICA"), SALON_BELLEZA("SALON_BELLEZA");

    private String value;

    private SiteTypes(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
