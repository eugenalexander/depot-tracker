package de.habrony.depottracker.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    private static final Symbol SAP = new Symbol("SAP");

    private static Trade buy(String quantity) {
        return new Trade(SAP, TradeType.BUY,
                new Quantity(new BigDecimal(quantity)),
                new Price(new BigDecimal("100")),
                LocalDate.of(2026, 10, 22));
    }

    private static Trade sell(String quantity) {
        return new Trade(SAP, TradeType.SELL,
                new Quantity(new BigDecimal(quantity)),
                new Price(new BigDecimal("100")),
                LocalDate.of(2026, 10, 22));
    }

    @Test
    void quantityIsZeroWithoutTrades() {
        Position position = new Position(SAP, List.of());
        assertEquals(BigDecimal.ZERO, position.quantity());
    }

    @Test
    void quantityIsSumOfBuys() {
        Position position = new Position(SAP, List.of(buy("10"), buy("20")));
        assertEquals(new BigDecimal("30"), position.quantity());
    }

    @Test
    void sellReducesQuantity() {
        Position position = new Position(SAP, List.of(buy("10"), sell("4")));
        assertEquals(new BigDecimal("6"), position.quantity());
    }
}
