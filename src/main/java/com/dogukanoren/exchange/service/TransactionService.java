package com.dogukanoren.exchange.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.dogukanoren.exchange.model.Transaction;
import com.dogukanoren.exchange.repository.TransactionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void saveTransaction(Transaction transaction) {

        transactionRepository.save(transaction);
    }

    public List<Transaction> findAllTransactions() {

        return transactionRepository.findAll();
    }

    public Page<Transaction> pageFilteredTransactions(int offset, int pageSize, List<Transaction> filtered) {

        Pageable pageable = PageRequest.of(offset, pageSize);

        return new PageImpl<>(filtered, pageable, filtered.size());
    }

    public List<Transaction> findTransactionsByDate(String dateParam) {

        List<Transaction> filteredTransaction = new ArrayList<>();

        for (int i = 0; i < transactionRepository.findAll().size(); i++) {
            if (transactionRepository.findAll().get(i).getTransactionDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .toString().equals(dateParam)) {
                filteredTransaction.add(transactionRepository.findAll().get(i));
            }
        }

        return filteredTransaction;
    }

    public List<Transaction> findTransactionsByID(String idParam) {

        List<Transaction> filteredTransaction = new ArrayList<>();

        for (int i = 0; i < transactionRepository.findAll().size(); i++) {
            if (transactionRepository.findAll().get(i).getTransactionId().equals(idParam)) {

                filteredTransaction.add(transactionRepository.findAll().get(i));
            }
        }

        return filteredTransaction;
    }

    public List<Transaction> findTransactionsIDandDate(String dateParam, String idParam) {

        List<Transaction> filteredTransaction = new ArrayList<>();

        for (int i = 0; i < transactionRepository.findAll().size(); i++) {
            if (transactionRepository.findAll().get(i).getTransactionDate()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                    .toString().equals(dateParam)
                    && transactionRepository.findAll().get(i).getTransactionId().equals(idParam)) {
                filteredTransaction.add(transactionRepository.findAll().get(i));
            }
        }

        return filteredTransaction;
    }

}
