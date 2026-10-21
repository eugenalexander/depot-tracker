package de.habrony.depottracker.domain;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuantityTest {

    @Test
    void acceptsFractionQuantity() {
        Quantity quantity = new Quantity(new BigDecimal("0.5"));
        assertEquals(new BigDecimal("0.5"), quantity.value());
    }

    @Test
    void rejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(null));
    }
    @Test
    void RejectsZero() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(new BigDecimal("0")));
    }
    @Test
    void rejectsNegative() {
        assertThrows(IllegalArgumentException.class, () -> new Quantity(new BigDecimal("-1")));
    }
}
