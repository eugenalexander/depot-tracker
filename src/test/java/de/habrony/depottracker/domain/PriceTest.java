package de.habrony.depottracker.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceTest {

    @Test
    void acceptsPriceAmount() {
        Price price = new Price(new BigDecimal("123.45"));
        assertEquals(new BigDecimal("123.45"), price.amount());
    }

    @Test
    void rejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Price(null));
    }
    @Test
    void rejectsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Price(BigDecimal.ZERO));
    }
    @Test
    void rejectsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Price(new BigDecimal("-1")));
    }
}
