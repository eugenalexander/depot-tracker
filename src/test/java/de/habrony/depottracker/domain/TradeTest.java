package de.habrony.depottracker.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TradeTest {

    private static final Symbol SYMBOL = new Symbol("SAP");
    private static final Quantity QUANTITY = new Quantity(new BigDecimal("10"));
    private static final Price PRICE = new Price(new BigDecimal("150.00"));
    private static final LocalDate DATE = LocalDate.of(2026, 10, 21);

    @Test
    void calculatesTotalAsQuantityTimesPrice() {
        Trade trade = new Trade(
                SYMBOL,
                TradeType.BUY,
                QUANTITY,
                PRICE,
                DATE);

        assertEquals(new BigDecimal("1500.00"), trade.total());
    }

    @Test
    void rejectsNullSymbol() {
        assertThrows(IllegalArgumentException.class, () -> new Trade(
                null, TradeType.BUY,
                QUANTITY,
                PRICE,
                DATE));
    }

    @Test
    void rejectsNullType() {
        assertThrows(IllegalArgumentException.class, () -> new Trade(
                SYMBOL,
                null,
                QUANTITY,
                PRICE,
                DATE));
    }

    @Test
    void rejectsNullQuantity() {
        assertThrows(IllegalArgumentException.class, () -> new Trade(
                SYMBOL,
                TradeType.BUY,
                null,
                PRICE,
                DATE));
    }

    @Test
    void rejectsNullPrice() {
        assertThrows(IllegalArgumentException.class, () -> new Trade(
                SYMBOL,
                TradeType.BUY,
                QUANTITY,
                null,
                DATE));
    }

    @Test
    void rejectsNullDate() {
        assertThrows(IllegalArgumentException.class, () -> new Trade(
                SYMBOL,
                TradeType.BUY,
                QUANTITY,
                PRICE,
                null));
    }
}
