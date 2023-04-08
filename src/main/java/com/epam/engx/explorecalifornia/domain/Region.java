package com.epam.engx.explorecalifornia.domain;

import java.util.Arrays;

public enum Region {
    Central_Coast("Central Coast"),
    Southern_California("Southern California"),
    Northern_California("Northern California"),
    Varies("Varies");

    private final String label;

    Region(String label) {
        this.label = label;
    }

    public static Region findByLabel(String byLabel) {
        return Arrays.stream(values())
            .filter(r -> r.label.equalsIgnoreCase(byLabel))
            .findAny()
            .orElse(null);
    }
}
