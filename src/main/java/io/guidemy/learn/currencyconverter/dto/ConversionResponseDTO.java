package io.guidemy.learn.currencyconverter.dto;

import io.guidemy.learn.currencyconverter.model.Currency;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ConversionResponseDTO {
    // TODO: add the required fields
    private Currency from;
    private Currency to;
    private BigDecimal originalAmount;
    private BigDecimal resultAmount;
}
