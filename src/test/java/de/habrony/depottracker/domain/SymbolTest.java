package de.habrony.depottracker.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SymbolTest {

    @Test
    void normalizesValueToTrimmedUpperCase() {
        Symbol symbol = new Symbol(" sap ");
        assertEquals("SAP", symbol.value());
    }

    @Test
    void throwsExceptionForNullValue() {
        assertThrows(IllegalArgumentException.class, () -> new Symbol(null));
    }

    @Test
    void throwsExceptionForEmptyValue() {
        assertThrows(IllegalArgumentException.class, () -> new Symbol(" "));
    }

}
