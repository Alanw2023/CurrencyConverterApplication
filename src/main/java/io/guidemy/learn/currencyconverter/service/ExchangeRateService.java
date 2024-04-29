package io.guidemy.learn.currencyconverter.service;

import io.guidemy.learn.currencyconverter.model.Currency;
import io.guidemy.learn.currencyconverter.model.EURBasedExchangeRate;
import io.guidemy.learn.currencyconverter.repository.ExchangeRateRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
    Do not modify this class
 */
@Service
public class ExchangeRateService {
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Value("classpath:rates.xml")
    private Resource inputFilePath;

    @PostConstruct
    public void init() throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document doc = builder.parse(inputFilePath.getFile());
        doc.getDocumentElement().normalize();

        List<EURBasedExchangeRate> EURBasedExchangeRates = new ArrayList<>();

        NodeList nodeList = doc.getElementsByTagName("Cube");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            NamedNodeMap attrList = node.getAttributes();

            Node currencyAttribute = attrList.getNamedItem("currency");
            Node rateAttribute = attrList.getNamedItem("rate");

            if (attrList.getLength() == 2
                    && !Objects.isNull(currencyAttribute)
                    && !Objects.isNull(rateAttribute)) {
                String currency = currencyAttribute.getNodeValue();
                String rate = rateAttribute.getNodeValue();

                EURBasedExchangeRate exchangeRate = EURBasedExchangeRate.builder()
                        .currency(Currency.valueOf(currency))
                        .rate(new BigDecimal(rate))
                        .build();

                EURBasedExchangeRates.add(exchangeRate);
            }
        }

        exchangeRateRepository.saveAll(EURBasedExchangeRates);
    }

    public BigDecimal getExchangeRate(Currency fromCurrency,
                                      Currency toCurrency) {
        // TODO: implement this method
        //fromCurrency / toCurrency
        BigDecimal fromCurrencyByRate = exchangeRateRepository.findRateByCurrency(fromCurrency);
        BigDecimal toByCurrencyByRate = exchangeRateRepository.findRateByCurrency(toCurrency);

        if (fromCurrency == toCurrency){
            return BigDecimal.ONE;
        }
        if (Currency.EUR == fromCurrency){
            return  toByCurrencyByRate;
        }
        if (Currency.EUR == toCurrency){
            return BigDecimal.ONE.divide(fromCurrencyByRate,6,RoundingMode.HALF_UP);
        }

        return BigDecimal.ONE.divide(fromCurrencyByRate,6,RoundingMode.HALF_UP).multiply(toByCurrencyByRate);

    }

    @Transactional
    public List<EURBasedExchangeRate> persistAll(List<EURBasedExchangeRate> EURBasedExchangeRates) {
        return exchangeRateRepository.saveAll(EURBasedExchangeRates);
    }
}
