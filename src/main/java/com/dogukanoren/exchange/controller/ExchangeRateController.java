package com.dogukanoren.exchange.controller;

import com.dogukanoren.exchange.model.ApiError;
import com.dogukanoren.exchange.model.Transaction;
import com.dogukanoren.exchange.service.ApiErrorService;
import com.dogukanoren.exchange.service.ExchangeService;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import com.dogukanoren.exchange.configuration.View;
import com.dogukanoren.exchange.exception.NotFoundException;

@RestController
@RequestMapping("/api")
public class ExchangeRateController {// http://localhost:8080/api/exchange/fromCurrency=?&toCurrency=?

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    ApiErrorService apiErrorService;

    @RequestMapping("/exchange/")
    @JsonView(View.Exchange.class)
    public ResponseEntity<Transaction> getExchangeRate(@RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("toCurrency") String toCurrency) {

        exchangeService.fillRatesFromAPI(); // send request to fill map

        Transaction exchangeRate = new Transaction(fromCurrency, 1, toCurrency,
                ExchangeService.getRate(fromCurrency.replace(" ", "").toUpperCase(),
                        toCurrency.replace(" ", "").toUpperCase()));

        if (exchangeRate.getExchangeRate() == -1.0d) {
            throw new NotFoundException("Given Currency is incorrect!");
        }

        ResponseEntity<Transaction> response = new ResponseEntity<>(exchangeRate, HttpStatus.OK);

        return response;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleCurrencyNotFound(NotFoundException exception, HttpServletRequest request) {

        ApiError error = new ApiError(404, exception.getMessage(), request.getServletPath());

        apiErrorService.saveFailedTransaction(error);

        return error;
    }
}
