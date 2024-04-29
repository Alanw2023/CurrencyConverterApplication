package io.guidemy.learn.currencyconverter.controller;

import io.guidemy.learn.currencyconverter.dto.ConversionResponseDTO;
import io.guidemy.learn.currencyconverter.dto.RateResponseDTO;
import io.guidemy.learn.currencyconverter.model.Currency;
import io.guidemy.learn.currencyconverter.model.EURBasedExchangeRate;
import io.guidemy.learn.currencyconverter.repository.ExchangeRateRepository;
import io.guidemy.learn.currencyconverter.service.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/rates")
public class ExchangeRateController {
    // TODO: inject the required bean(s)
    @Autowired
    private ExchangeRateService exchangeRateService;

    // TODO: implement the controller methods
    @GetMapping
    public RateResponseDTO getExchangeRate (@RequestParam("f") Currency from,
                                            @RequestParam("t") Currency to){
        BigDecimal rate = exchangeRateService.getExchangeRate(from, to);
        //Currency * Rate
        RateResponseDTO response = RateResponseDTO.builder()
                .from(from)
                .to(to)
                .exchangeRate(rate)
                .build();
    return response;}

    @GetMapping("/convert")
    public ConversionResponseDTO convert (@RequestParam("f") Currency from,
                                          @RequestParam("t") Currency to,
                                          @RequestParam("a")BigDecimal amount){
        //Currency * Rate * Amount
        BigDecimal rate = exchangeRateService.getExchangeRate(from, to);

        BigDecimal multiply = amount.multiply(rate);

        ConversionResponseDTO build = ConversionResponseDTO.builder()
                .from(from)
                .to(to)
                .originalAmount(amount)
                .resultAmount(multiply)
                .build();
        return build;}
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @GetMapping("/test")
    public BigDecimal convert (){
        //Currency * Rate * Amount
        return exchangeRateRepository.findRateByCurrency(Currency.USD);



    }
}
