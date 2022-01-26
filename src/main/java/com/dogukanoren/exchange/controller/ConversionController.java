package com.dogukanoren.exchange.controller;

import javax.servlet.http.HttpServletRequest;

import com.dogukanoren.exchange.configuration.View;
import com.dogukanoren.exchange.exception.NotFoundException;
import com.dogukanoren.exchange.model.ApiError;
import com.dogukanoren.exchange.model.Transaction;
import com.dogukanoren.exchange.service.ApiErrorService;
import com.dogukanoren.exchange.service.ExchangeService;
import com.dogukanoren.exchange.service.TransactionService;
import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ConversionController {

    // http://localhost:8080/api/conversion/?fromCurrency=TRY&fromAmount=100&toCurrency=EUR

    @Autowired
    TransactionService transactionService;

    @Autowired
    ExchangeService exchangeService;

    @Autowired
    ApiErrorService apiErrorService;

    @RequestMapping("/conversion/")
    @JsonView(View.Conversion.class)
    public ResponseEntity<Transaction> getConversion(@RequestParam("fromCurrency") String fromCurrency,
            @RequestParam("fromAmount") double fromAmount,
            @RequestParam("toCurrency") String toCurrency) {

        exchangeService.fillRatesFromAPI(); // filling map

        Transaction exchangeRate = new Transaction(fromCurrency, fromAmount, toCurrency,
                ExchangeService
                        .getRate(fromCurrency.replace(" ", "").toUpperCase(), toCurrency.replace(" ", "").toUpperCase())
                        .doubleValue()); // creating new transaction
                                         // trimming and correcting case

        if (exchangeRate.getExchangeRate() == -1.0d) {
            throw new NotFoundException("Given Currency is incorrect!");
        }

        transactionService.saveTransaction(exchangeRate); // saving successfull transaction to db

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
