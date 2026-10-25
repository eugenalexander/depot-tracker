package de.habrony.depottracker.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Portfolio {

    private final Map<Symbol, Position> positions;

    public Portfolio(List<Trade> trades) {
        if (trades == null) {
            throw new IllegalArgumentException("Portfolio trades must not be null");
        }

        Map<Symbol, List<Trade>> tradesBySymbol = new HashMap<>();
        for (Trade trade : trades) {
            Symbol symbol = trade.symbol();
            if (!tradesBySymbol.containsKey(symbol)) {
                tradesBySymbol.put(symbol, new ArrayList<>());
            }
            tradesBySymbol.get(symbol).add(trade);
        }

        Map<Symbol, Position> result = new HashMap<>();
        for (Symbol symbol : tradesBySymbol.keySet()) {
            result.put(symbol, new Position(symbol, tradesBySymbol.get(symbol)));
        }
        this.positions = Map.copyOf(result);
    }

    public Position position(Symbol symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException("Symbol must not be null");
        }
        return positions.getOrDefault(symbol, new Position(symbol, List.of()));
    }

    public Set<Symbol> symbols() {
        return positions.keySet();
    }
}