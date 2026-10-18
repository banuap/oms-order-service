package com.atlas.demo.oms;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;

public record OrderRequest(
        @NotBlank
        @Pattern(regexp = "[A-Z][A-Z0-9.]{0,9}", message = "must be an uppercase symbol")
        String symbol,
        @NotNull @DecimalMin(value = "0.0001")
        BigDecimal quantity,
        @NotNull @DecimalMin(value = "0.01")
        BigDecimal limitPrice,
        @NotNull @DecimalMin(value = "0.00")
        BigDecimal availableBuyingPower,
        boolean restrictedSecurity) {
}
