package com.banking.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.banking.model.Transaction;
import com.banking.util.DBConnection;

public class TransactionDAO {

	public List<Transaction> getRecentTransactions(String accountNo) {

	    List<Transaction> list = new ArrayList<>();

	    String sql =
	        "SELECT * FROM TRANSACTION_HISTORY " +
	        "WHERE ACCOUNT_NO = ? " +
	        "ORDER BY TRANSACTION_DATE DESC " +
	        "FETCH FIRST 5 ROWS ONLY";

	    try (
	        Connection con = DBConnection.getConnection();
	        PreparedStatement ps = con.prepareStatement(sql);
	    ) {

	        ps.setString(1, accountNo);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {

	            Transaction t = new Transaction();

	            t.setTransactionId(rs.getInt("TRANSACTION_ID"));
	            t.setAccountNo(rs.getString("ACCOUNT_NO"));
	            t.setTransactionType(rs.getString("TRANSACTION_TYPE"));
	            t.setAmount(rs.getDouble("AMOUNT"));
	            t.setBalanceAfter(rs.getDouble("BALANCE_AFTER"));
	            t.setTransactionDate(rs.getTimestamp("TRANSACTION_DATE"));

	            list.add(t);
	        }

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    return list;
	}

}