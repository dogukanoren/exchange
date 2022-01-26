package com.dogukanoren.exchange.model;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.dogukanoren.exchange.configuration.View;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Data;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue
    Long id;

    @JsonView(View.ConversionList.class)
    private String fromCurrency;

    @JsonView(View.ConversionList.class)
    private double fromAmount;

    @JsonView(View.Conversion.class)
    private String toCurrency;

    @JsonView(View.Conversion.class)
    private double toAmount;

    @JsonView(View.Exchange.class)
    private double exchangeRate;

    @JsonView(View.Conversion.class)
    private String transactionId;

    @JsonView(View.Conversion.class)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transactionDate;

    public Transaction() {
    }

    public Transaction(String fromCurrency, double fromAmount, String toCurrency,
            double exchangeRate) {
        this.fromCurrency = fromCurrency;
        this.fromAmount = fromAmount;
        this.toCurrency = toCurrency;
        this.toAmount = fromAmount * exchangeRate;
        this.exchangeRate = exchangeRate;
        this.transactionId = UUID.randomUUID().toString().toUpperCase().replace("-", "");
        this.transactionDate = LocalDateTime.now(ZoneId.of("GMT+03:00"));
    }

    public String getfromCurrency() {
        return fromCurrency;
    }

    public double getFromAmount() {
        return fromAmount;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public double getToAmount() {
        return toAmount;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

}
