package de.habrony.depottracker.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Trade(Symbol symbol, TradeType type, Quantity quantity, Price price, LocalDate date) {

    public Trade {
        if (symbol == null) {
            throw new IllegalArgumentException("Trade symbol must not be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("Trade type must not be null");
        }
        if (quantity == null) {
            throw new IllegalArgumentException("Trade quantity must not be null");
        }
        if (price == null) {
            throw new IllegalArgumentException("Trade price must not be null");
        }
        if (date == null) {
            throw new IllegalArgumentException("Trade date must not be null");
        }
    }

    public BigDecimal total() {
        return quantity.value().multiply(price.amount());
    }
}
