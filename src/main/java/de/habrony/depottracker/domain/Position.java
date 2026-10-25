package de.habrony.depottracker.domain;

import java.math.BigDecimal;
import java.util.List;

public final class Position {

    private final Symbol symbol;
    private final List<Trade> trades;
    private final BigDecimal quantity;

    public Position(Symbol symbol, List<Trade> trades) {
        this.symbol = symbol;
        this.trades = List.copyOf(trades);

        BigDecimal total = BigDecimal.ZERO;
        for (Trade trade : this.trades) {
            switch (trade.type()) {
                case BUY -> total = total.add(trade.quantity().value());
                case SELL -> total = total.subtract(trade.quantity().value());
            }
            if (total.signum() < 0) {
                throw new IllegalArgumentException(
                        "Cannot sell more than held for " + symbol.value());
            }
        }
        this.quantity = total;
    }

    public BigDecimal quantity() {
        return quantity;
    }
}