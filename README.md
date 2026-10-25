# Depot-Tracker

A portfolio tracker for stock trades: record buys and sells, and get the current
quantity and weighted average purchase price per stock.

> **Status: work in progress.** Built step by step as a learning and portfolio
> project. The domain model is done; persistence, REST API and frontend are next.

## What it does today

- Validated value objects: `Symbol`, `Quantity`, `Price` (invalid values cannot be created)
- `Trade`: a buy or sell of a symbol, with quantity, price and date
- `Position`: all trades of one symbol, with
    - current quantity
    - weighted average price (4 decimals, `HALF_UP`)
    - rejection of sells that exceed the held quantity
- Money and quantities use `BigDecimal`, never `double`
- Fractional shares supported

## Tech stack

| Layer        | Technology                         | Status   |
|--------------|------------------------------------|----------|
| Domain       | Java 25, JUnit 6                   | done     |
| Architecture | Hexagonal (ports & adapters)       | in progress |
| Persistence  | Spring Boot 4, JPA, PostgreSQL     | planned  |
| API          | REST                               | planned  |
| Frontend     | React, TypeScript                  | planned  |
| Ops          | Docker Compose, CI, k3d            | planned  |

## Run the tests

Requires Java 25 and Maven.

    mvn test

## Design notes

- The domain has no framework dependencies. Spring and the database are added
  later as adapters around it.
- Business rules live in exactly one place: each value object validates itself,
  so the rest of the code can rely on valid values.
- Developed test-first: every rule has a test written before the code.

## Roadmap

- [x] Domain model with tests
- [x] Portfolio across multiple symbols
- [ ] Ports and use cases
- [ ] Spring Boot + PostgreSQL adapters
- [ ] REST API
- [ ] React + TypeScript frontend
- [ ] Docker Compose, CI pipeline, local Kubernetes (k3d)