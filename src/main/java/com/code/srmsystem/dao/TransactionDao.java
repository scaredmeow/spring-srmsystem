package com.code.srmsystem.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.code.srmsystem.model.Transaction;

@Component
public interface TransactionDao {
    public boolean saveTransaction(Transaction transaction);

    public boolean updateLatestTransaction(String payment);

    public boolean updateSpecificTransaction(String payment, int TID);

    public List<Transaction> getUnpaidTransactions();

    public Transaction findByTransactionID(int TID);
}
