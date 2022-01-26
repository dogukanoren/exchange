package com.dogukanoren.exchange.repository;

import com.dogukanoren.exchange.model.ApiError;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiErrorRepository extends JpaRepository<ApiError, Long> {

}
