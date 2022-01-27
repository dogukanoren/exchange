package com.dogukanoren.exchange.service;

import java.util.HashMap;
import java.util.Map;

import com.dogukanoren.exchange.model.ExchangeRate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@Service
public class ExchangeService {
    @Autowired
    private RestTemplate restTemplate;

    private static Map<String, Double> currencyRates = new HashMap<>();

    final String accessKey = "3936dd48810d37ef8d9a96561f8e0c9f"; // get from application properties
    final String uri = "http://api.exchangeratesapi.io/v1/latest?access_key=" + accessKey;

    public static Double getRate(String fromCurrency, String toCurrency) {

        try {
            return currencyRates.get(toCurrency) / currencyRates.get(fromCurrency);
        } catch (Exception e) {
            return -1.0d;
        }

    }

    public static void clearRates() {

        currencyRates.clear();

    }

    public static void fillRates(Map<String, Double> map) {

        currencyRates = map;

    }

    @GetMapping
    public void fillRatesFromAPI() {

        clearRates();
        ExchangeRate rates = restTemplate.getForObject(uri, ExchangeRate.class);

        fillRates(rates.getRates());

    }

}
