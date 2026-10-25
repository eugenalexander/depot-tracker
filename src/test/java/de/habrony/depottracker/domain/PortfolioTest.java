package de.habrony.depottracker.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PortfolioTest {

    private static final Symbol SAP = new Symbol("SAP");
    private static final Symbol AAPL = new Symbol("AAPL");
    private static final Symbol BMW = new Symbol("BMW");

    private static Trade buy(Symbol symbol, String quantity) {
        return new Trade(symbol, TradeType.BUY,
                new Quantity(new BigDecimal(quantity)),
                new Price(new BigDecimal("100")),
                LocalDate.of(2026, 10, 25));
    }

    private static Trade sell(Symbol symbol, String quantity) {
        return new Trade(symbol, TradeType.SELL,
                new Quantity(new BigDecimal(quantity)),
                new Price(new BigDecimal("100")),
                LocalDate.of(2026, 10, 25));
    }

    @Test
    void emptyPortfolioHasNoSymbols() {
        Portfolio portfolio = new Portfolio(List.of());
        assertTrue(portfolio.symbols().isEmpty());
    }

    @Test
    void groupsTradesBySymbol() {
        Portfolio portfolio = new Portfolio(List.of(
                buy(SAP, "10"),
                buy(AAPL, "5"),
                buy(SAP, "10"),
                sell(AAPL, "2")
        ));

        assertEquals(new BigDecimal("20"), portfolio.position(SAP).quantity());
        assertEquals(new BigDecimal("3"), portfolio.position(AAPL).quantity());
    }

    @Test
    void containsEachSymbolOnce() {
        Portfolio portfolio = new Portfolio(List.of(
                buy(SAP, "10"),
                buy(AAPL, "5"),
                buy(SAP, "10")
        ));

        assertEquals(Set.of(SAP, AAPL), portfolio.symbols());
    }

    @Test
    void unknownSymbolHasZeroQuantity() {
        Portfolio portfolio = new Portfolio(List.of(buy(SAP, "10")));
        assertEquals(BigDecimal.ZERO, portfolio.position(BMW).quantity());
    }

    @Test
    void rejectsNullTrades() {
        assertThrows(IllegalArgumentException.class, () -> new Portfolio(null));
    }

    @Test
    void rejectsSellingMoreThanHeldForOneSymbol() {
        assertThrows(IllegalArgumentException.class, () -> new Portfolio(List.of(
                buy(SAP, "5"),
                buy(AAPL, "100"),
                sell(SAP, "6")
        )));
    }
}
