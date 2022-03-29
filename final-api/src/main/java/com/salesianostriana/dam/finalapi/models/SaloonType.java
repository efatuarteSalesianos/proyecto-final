package com.salesianostriana.dam.finalapi.models;

public enum SaloonType {

    PELUQUERIA("PELUQUERIA"),
    BARBERIA("BARBERIA"),
    SALON_BELLEZA("SALON_BELLEZA"),
    CENTRO_ESTETICA("CENTRO_ESTETICA");

    private String value;

    private SaloonType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
