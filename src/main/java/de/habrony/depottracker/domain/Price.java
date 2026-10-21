package de.habrony.depottracker.domain;

import java.math.BigDecimal;

public record Price(BigDecimal amount) {

    public Price {
        if(amount == null) {
            throw new IllegalArgumentException("Price amount must not be null");
        }
        if(amount.signum() <= 0) {
            throw new IllegalArgumentException("Price amount must be positive: " + amount);
        }
    }
}
