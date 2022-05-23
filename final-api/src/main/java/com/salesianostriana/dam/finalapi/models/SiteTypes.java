package com.salesianostriana.dam.finalapi.models;

public enum SiteTypes {
    PELUQUERIA("Peluquería"),
    BARBERIA("Barbería"),
    CENTRO_ESTETICA("Centro de Estética"),
    SALON_BELLEZA("Salón de Belleza");

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
