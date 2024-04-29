package io.guidemy.learn.currencyconverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.guidemy.learn.currencyconverter.model.Currency;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CurrencyConverterSmokeTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetExchangeRate() throws Exception {
        Currency fromCurrency = Currency.USD;
        Currency toCurrency = Currency.EUR;

        mockMvc.perform(get("/api/rates")
                        .param("f", fromCurrency.toString())
                        .param("t", toCurrency.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value(fromCurrency.toString()))
                .andExpect(jsonPath("$.to").value(toCurrency.toString()))
                .andExpect(jsonPath("$.exchangeRate").exists());
    }

    @Test
    public void testConvert() throws Exception {
        Currency fromCurrency = Currency.USD;
        Currency toCurrency = Currency.EUR;
        BigDecimal amount = new BigDecimal("100");

        mockMvc.perform(get("/api/rates/convert")
                        .param("f", fromCurrency.toString())
                        .param("t", toCurrency.toString())
                        .param("a", amount.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.from").value(fromCurrency.toString()))
                .andExpect(jsonPath("$.to").value(toCurrency.toString()))
                .andExpect(jsonPath("$.originalAmount").value(amount.toString()))
                .andExpect(jsonPath("$.resultAmount").exists());
    }
}
