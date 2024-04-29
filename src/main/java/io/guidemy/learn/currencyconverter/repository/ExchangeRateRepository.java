package io.guidemy.learn.currencyconverter.repository;

import io.guidemy.learn.currencyconverter.model.Currency;
import io.guidemy.learn.currencyconverter.model.EURBasedExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ExchangeRateRepository extends JpaRepository<EURBasedExchangeRate, Long> {
    // TODO: implement this query
    @Query("SELECT e.rate FROM EURBasedExchangeRate e WHERE e.currency = :currency")
    BigDecimal findRateByCurrency(Currency currency);
}
