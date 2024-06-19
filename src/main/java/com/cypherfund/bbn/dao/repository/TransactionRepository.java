package com.cypherfund.bbn.dao.repository;

import com.cypherfund.bbn.dao.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}