package com.atlas.demo.oms;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String symbol,
        BigDecimal quantity,
        BigDecimal limitPrice,
        BigDecimal notional,
        String status,
        Instant createdAt) {
}
