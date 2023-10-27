package com.example.demo.enums;

public enum Status {
    ABSOLVIERT ("Absolviert"),
    AUF_FAHRT ("Auf Fahrt"),
    ZUKUENFTIG ("Zukünftig")
    ;

    private final String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
