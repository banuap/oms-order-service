package com.atlas.demo.oms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class OrderServiceTest {
    private final OrderService service = new OrderService();

    @Test
    void acceptsOrderWithinBuyingPowerAndApprovalThreshold() {
        OrderResponse order = service.submit(new OrderRequest(
                "ACME", new BigDecimal("10"), new BigDecimal("20"),
                new BigDecimal("500"), false));

        assertEquals("ACCEPTED", order.status());
        assertEquals(0, new BigDecimal("200").compareTo(order.notional()));
    }

    @Test
    void holdsLargeOrderForDeskApproval() {
        OrderResponse order = service.submit(new OrderRequest(
                "ACME", new BigDecimal("1001"), new BigDecimal("500"),
                new BigDecimal("600000"), false));

        assertEquals("PENDING_DESK_APPROVAL", order.status());
    }

    @Test
    void rejectsRestrictedSecurity() {
        OrderRejectedException error = assertThrows(OrderRejectedException.class, () ->
                service.submit(new OrderRequest(
                        "ACME", BigDecimal.ONE, BigDecimal.TEN, new BigDecimal("100"), true)));

        assertEquals("Security is on the restricted list.", error.getMessage());
    }

    @Test
    void rejectsInsufficientBuyingPower() {
        assertThrows(OrderRejectedException.class, () ->
                service.submit(new OrderRequest(
                        "ACME", new BigDecimal("10"), new BigDecimal("20"),
                        new BigDecimal("100"), false)));
    }
}
