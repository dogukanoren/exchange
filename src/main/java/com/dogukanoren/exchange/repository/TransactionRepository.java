package com.dogukanoren.exchange.repository;

import com.dogukanoren.exchange.model.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
