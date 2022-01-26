package com.dogukanoren.exchange.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dogukanoren.exchange.exception.BadRequestException;
import com.dogukanoren.exchange.model.ApiError;
import com.dogukanoren.exchange.model.Transaction;
import com.dogukanoren.exchange.service.ApiErrorService;
import com.dogukanoren.exchange.service.TransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api")
public class ConversionListController {

    // http://localhost:8080/api/controller-list/

    @Autowired
    TransactionService transactionService;

    @Autowired
    ApiErrorService apiErrorService;

    @RequestMapping("/conversion-list/{offSet}/{pageSize}")
    public Page<Transaction> filterConversions(@RequestParam(value = "dateParam", required = false) String dateParam,
            @RequestParam(value = "idParam", required = false) String idParam, @PathVariable("offSet") int offSet,
            @PathVariable("pageSize") int pageSize) {

        // http://localhost:8080/api/conversion-list/1/2/?dateParam=2022-01-25

        // handling request to control if the parameters are null

        if ((dateParam != null && dateParam != "") && (idParam != null && idParam != "")) {
            return transactionService.pageFilteredTransactions(offSet, pageSize,
                    transactionService.findTransactionsIDandDate(dateParam, idParam));
        } else if (dateParam != null && dateParam != "") {
            return transactionService.pageFilteredTransactions(offSet, pageSize,
                    transactionService.findTransactionsByDate(dateParam));
        } else if (idParam != null && idParam != "") {
            return transactionService.pageFilteredTransactions(offSet, pageSize,
                    transactionService.findTransactionsByID(idParam));
        } else {
            throw new BadRequestException("Both parameters can not be null!");
        }
    }

    @RequestMapping("/conversion-list/showAll")
    public List<Transaction> showAllConversions() {

        return transactionService.findAllTransactions();
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleBadRequest(BadRequestException exception, HttpServletRequest request) {

        ApiError error = new ApiError(400, exception.getMessage(), request.getServletPath());

        apiErrorService.saveFailedTransaction(error);

        return error;
    }

}
