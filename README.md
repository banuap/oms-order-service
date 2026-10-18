# Order Management Service

Java 17 / Spring Boot API starter for a synthetic order-entry flow:

- `POST /api/v1/orders` validates input, restricted-list status, and buying
  power against limit-price notional.
- Orders above USD 500,000 are recorded as `PENDING_DESK_APPROVAL`; smaller
  passing orders are `ACCEPTED`.
- `GET /api/v1/orders/{id}` retrieves an order held in process memory.
- `GET /health` reports service health.

## Run and test

```sh
mvn test
mvn spring-boot:run
```

The API listens on port 8080. Use a symbol, positive quantity and price, and
available buying power in the request. `restrictedSecurity` is a synthetic
input flag, not a connected compliance list. State is in memory and resets
when the process restarts.

This code does not route or execute orders and is not connected to venues,
accounts, authorization, durable storage, market data, or compliance systems.
Use synthetic data only; do not deploy it for trading or regulated decisions.
