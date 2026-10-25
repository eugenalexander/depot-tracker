package de.habrony.depottracker.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PositionTest {

    private static final Symbol SAP = new Symbol("SAP");

    private static Trade buy(String quantity, String price) {
        return new Trade(SAP, TradeType.BUY,
                new Quantity(new BigDecimal(quantity)),
                new Price(new BigDecimal(price)),
                LocalDate.of(2026, 10, 22));
    }

    private static Trade sell(String quantity, String price) {
        return new Trade(SAP, TradeType.SELL,
                new Quantity(new BigDecimal(quantity)),
                new Price(new BigDecimal(price)),
                LocalDate.of(2026, 10, 22));
    }

    private static Trade buy(String quantity) {
        return buy(quantity, "100");
    }

    private static Trade sell(String quantity) {
        return sell(quantity, "100");
    }

    // --- quantity ---

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
    void sellAfterBuyReducesQuantity() {
        Position position = new Position(SAP, List.of(buy("10"), sell("4")));
        assertEquals(new BigDecimal("6"), position.quantity());
    }

    @Test
    void rejectsSellingMoreThanHeld() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position(SAP, List.of(buy("10"), sell("11"))));
    }

    @Test
    void allowsSellingEverything() {
        Position position = new Position(SAP, List.of(buy("10"), sell("10")));
        assertEquals(BigDecimal.ZERO, position.quantity());
    }

    @Test
    void rejectsSellBeforeBuy() {
        assertThrows(IllegalArgumentException.class,
                () -> new Position(SAP, List.of(sell("5"), buy("10"))));
    }

    // --- average price ---

    @Test
    void averagePriceIsZeroWithoutTrades() {
        Position position = new Position(SAP, List.of());
        assertEquals(BigDecimal.ZERO, position.averagePrice());
    }

    @Test
    void averagePriceOfSingleBuyIsItsPrice() {
        Position position = new Position(SAP, List.of(buy("10", "100")));
        assertEquals(new BigDecimal("100.0000"), position.averagePrice());
    }

    @Test
    void averagePriceIsWeightedOverBuys() {
        Position position = new Position(SAP, List.of(
                buy("10", "100"),
                buy("10", "120")));
        assertEquals(new BigDecimal("110.0000"), position.averagePrice());
    }

    @Test
    void sellDoesNotChangeAveragePrice() {
        Position position = new Position(SAP, List.of(
                buy("10", "100"),
                buy("10", "120"),
                sell("5", "130")));
        assertEquals(new BigDecimal("110.0000"), position.averagePrice());
    }

    @Test
    void averagePriceIsZeroAfterSellingEverything() {
        Position position = new Position(SAP, List.of(
                buy("10", "100"),
                sell("10", "130")));
        assertEquals(BigDecimal.ZERO, position.averagePrice());
    }

    @Test
    void averagePriceIsRoundedToFourDecimals() {
        Position position = new Position(SAP, List.of(
                buy("1", "10"),
                buy("2", "20")));
        assertEquals(new BigDecimal("16.6667"), position.averagePrice());
    }
}