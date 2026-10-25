package de.habrony.depottracker.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class Position {

    private static final int PRICE_SCALE = 4;

    private final Symbol symbol;
    private final List<Trade> trades;
    private final BigDecimal quantity;
    private final BigDecimal averagePrice;

    public Position(Symbol symbol, List<Trade> trades) {
        this.symbol = symbol;
        this.trades = List.copyOf(trades);

        BigDecimal total = BigDecimal.ZERO;
        BigDecimal average = BigDecimal.ZERO;

        for (Trade trade : this.trades) {
            BigDecimal tradeQuantity = trade.quantity().value();
            switch (trade.type()) {
                case BUY -> {
                    BigDecimal oldCost = average.multiply(total);
                    total = total.add(tradeQuantity);
                    average = oldCost.add(trade.total())
                            .divide((total), PRICE_SCALE, RoundingMode.HALF_UP);
                }
                case SELL -> total = total.subtract(tradeQuantity);
            }
            if (total.signum() < 0) {
                throw new IllegalArgumentException(
                        "Cannot sell more than held for " + symbol.value());
            }
            if (total.signum() == 0) {
                average = BigDecimal.ZERO;
            }
        }
        this.quantity = total;
        this.averagePrice = average;
    }

    public BigDecimal quantity() {
        return quantity;
    }

    public BigDecimal averagePrice() {
        return averagePrice;
    }
}