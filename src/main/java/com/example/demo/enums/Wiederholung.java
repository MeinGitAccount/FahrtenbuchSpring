package com.example.demo.enums;

public enum Wiederholung {
    WOECHENTLICH ("woechentlich"),
    MONATLICH ("monatlich"),
    JAEHRLICH ("jaehrlich"),
    NICHT_DEFINIERT ("")
    ;

    private final String label;

    Wiederholung(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
