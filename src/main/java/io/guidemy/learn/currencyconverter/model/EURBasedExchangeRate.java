package io.guidemy.learn.currencyconverter.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/*
    Do not modify this class
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "eur_based_exchange_rates")
public class EURBasedExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    private BigDecimal rate;
}
