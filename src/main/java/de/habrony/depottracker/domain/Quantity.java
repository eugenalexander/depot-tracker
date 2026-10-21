package de.habrony.depottracker.domain;

import java.math.BigDecimal;

public record Quantity(BigDecimal value) {

    public Quantity {
        if (value == null) {
            throw new IllegalArgumentException("Quantity value must not be null");
        }
        if (value.signum() <= 0) {
            throw new IllegalArgumentException("Quantity value must be positive: " + value);
        }
    }
}
