package de.habrony.depottracker.domain;

import java.util.Locale;

public record Symbol(String value) {

    public Symbol {
        if (value == null) {
            throw new IllegalArgumentException("value must not be null");
        }
        if (value.isBlank()) {
            throw new IllegalArgumentException("value must not be blank");
        }
        value = value.trim().toUpperCase(Locale.ROOT);
    }
}
