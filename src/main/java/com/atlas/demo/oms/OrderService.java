package com.atlas.demo.oms;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private static final BigDecimal DESK_APPROVAL_THRESHOLD = new BigDecimal("500000.00");
    private final ConcurrentMap<UUID, OrderResponse> orders = new ConcurrentHashMap<>();

    public OrderResponse submit(OrderRequest request) {
        BigDecimal notional = request.quantity().multiply(request.limitPrice());
        if (request.restrictedSecurity()) {
            throw new OrderRejectedException("Security is on the restricted list.");
        }
        if (request.availableBuyingPower().compareTo(notional) < 0) {
            throw new OrderRejectedException("Available buying power does not cover the order notional.");
        }

        String status = notional.compareTo(DESK_APPROVAL_THRESHOLD) > 0
                ? "PENDING_DESK_APPROVAL"
                : "ACCEPTED";
        OrderResponse response = new OrderResponse(
                UUID.randomUUID(), request.symbol(), request.quantity(), request.limitPrice(),
                notional, status, Instant.now());
        orders.put(response.id(), response);
        return response;
    }

    public OrderResponse find(UUID id) {
        return orders.get(id);
    }
}
