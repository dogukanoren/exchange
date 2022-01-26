package com.dogukanoren.exchange.service;

import com.dogukanoren.exchange.model.ApiError;
import com.dogukanoren.exchange.repository.ApiErrorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApiErrorService {

    @Autowired
    ApiErrorRepository apiErrorRepository;

    public void saveFailedTransaction(ApiError failedTransaction) {

        apiErrorRepository.save(failedTransaction);
    }
}
