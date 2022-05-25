package com.salesianostriana.dam.finalapi.models;

public enum DaysOfWeek {

    LUNES("Lunes"), MARTES("Martes"), MIERCOLES("Miércoles"), JUEVES("Jueves"), VIERNES("Viernes"),
    SABADO("Sábado"), DOMINGO("Domingo");

    private String name;

    DaysOfWeek(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
