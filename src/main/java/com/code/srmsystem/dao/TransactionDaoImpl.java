package com.code.srmsystem.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.code.srmsystem.model.Transaction;
import com.code.srmsystem.model.User;
import com.code.srmsystem.service.AuthService;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private UserDao userDao;
    private AuthService authService;

    public TransactionDaoImpl(UserDao userDao, AuthService authService) {
        this.userDao = userDao;
        this.authService = authService;
    }

    @Override
    public boolean saveTransaction(Transaction transaction) {
        String sql = "INSERT INTO transactions(UID,documents) VALUES(?, ?)";
        return (jdbcTemplate.update(sql,
                transaction.getUID(),
                transaction.getDocuments()) == 1);
    }

    @Override
    public boolean updateLatestTransaction(String payment) {
        String sql = "UPDATE transactions SET payment= ?, status='PAID' WHERE status='UNPAID' AND UID = ? ORDER BY created_at DESC LIMIT 1";
        User curr_user = this.userDao.findByUserName(this.authService.getUser());
        return jdbcTemplate.update(
                sql,
                payment,
                curr_user.getUID()) == 1;
    }

    @Override
    public List<Transaction> getUnpaidTransactions() {
        String sql = "SELECT * FROM transactions WHERE status='UNPAID' AND UID = ? ORDER BY created_at DESC";
        User curr_user = this.userDao.findByUserName(this.authService.getUser());
        List<Transaction> listOfTransactions = jdbcTemplate.query(sql, new RowMapper<Transaction>() {
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");

            @Override
            public Transaction mapRow(ResultSet rs, int rowNum) throws SQLException {
                Transaction transaction = new Transaction();
                String date = dateFormat.format(rs.getTimestamp("created_at"));
                transaction.setCreated_at(date);
                transaction.setDocuments(rs.getString("documents"));
                transaction.setStatus(rs.getString("status"));
                transaction.setTID(rs.getInt("TID"));
                return transaction;
            }
        }, new Object[] { curr_user.getUID() });
        return listOfTransactions;
    }

    @Override
    public Transaction findByTransactionID(int TID) {
        String sql = "SELECT * FROM transactions WHERE TID = " + TID;
        return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Transaction.class));
    }

    @Override
    public boolean updateSpecificTransaction(String payment, int TID) {
        String sql = "UPDATE transactions SET payment= ?, status='PAID' WHERE status='UNPAID' AND TID = ? ORDER BY created_at DESC LIMIT 1";
        return jdbcTemplate.update(
                sql,
                payment,
                TID) == 1;
    }

    @Override
    public int getLatestTransactionID() {
        String sql = "SELECT T.TID FROM transactions as T ORDER BY created_at DESC LIMIT 1";
        return jdbcTemplate.queryForObject(sql, int.class);
    }

    @Override
    public boolean deleteTransaction(int TID) {
        String sql = "DELETE FROM transactions WHERE TID = ?";
        Object[] args = new Object[] { TID };
        return jdbcTemplate.update(sql, args) == 1;
    }

}
