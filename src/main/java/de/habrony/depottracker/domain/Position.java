package de.habrony.depottracker.domain;

import java.math.BigDecimal;
import java.util.List;

public final class Position {

    private final Symbol symbol;
    private final List<Trade> trades;

    public Position(Symbol symbol, List<Trade> trades) {
        this.symbol = symbol;
        this.trades = List.copyOf(trades);
    }
    public BigDecimal quantity() {
        BigDecimal total = BigDecimal.ZERO;
        for(Trade trade: trades) {
            switch (trade.type()) {
                case BUY -> total = total.add(trade.quantity().value());
                case SELL -> total = total.subtract(trade.quantity().value());
            }
        }
        return total;
    }
}
