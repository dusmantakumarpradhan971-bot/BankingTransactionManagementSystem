package com.banking.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.banking.model.Transaction;
import com.banking.model.Account;
import com.banking.util.DBConnection;

public class AccountDAO {

    public Account login(String accountNo, String password) {

        Account account = null;

        try (Connection con = DBConnection.getConnection()) {

        	String sql =
        			"SELECT ACCOUNT_NO, ACCOUNT_HOLDER, BALANCE, STATUS, EMAIL, MOBILE, ADDRESS " +
        			"FROM ACCOUNT WHERE ACCOUNT_NO=? AND PASSWORD=? AND STATUS='ACTIVE'";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, accountNo);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccountNo(rs.getString("ACCOUNT_NO"));
                account.setAccountHolder(rs.getString("ACCOUNT_HOLDER"));
                account.setBalance(rs.getDouble("BALANCE"));
                account.setStatus(rs.getString("STATUS"));
                account.setEmail(rs.getString("EMAIL"));
                account.setMobile(rs.getString("MOBILE"));
                account.setAddress(rs.getString("ADDRESS"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return account;
    }

    public boolean depositMoney(String acc, double amt) throws Exception {

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Update Balance
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ACCOUNT SET BALANCE = BALANCE + ? WHERE ACCOUNT_NO=?");

            ps.setDouble(1, amt);
            ps.setString(2, acc);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                con.rollback();
                return false;
            }

            // Get Updated Balance
            PreparedStatement balPs = con.prepareStatement(
                    "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO=?");

            balPs.setString(1, acc);

            ResultSet rs = balPs.executeQuery();

            double balanceAfter = 0;

            if (rs.next()) {
                balanceAfter = rs.getDouble("BALANCE");
            }

            // Save Transaction History
            saveTransaction(con,
                    acc,
                    "DEPOSIT",
                    amt,
                    balanceAfter);

            con.commit();

            return true;

        } catch (Exception e) {

            if (con != null) {
                con.rollback();
            }

            throw e;

        } finally {

            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }

        }
    }
    public boolean withdrawMoney(String acc, double amt) throws Exception {

        Connection con = null;

        try {

            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            // Check current balance
            PreparedStatement check = con.prepareStatement(
                    "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO=?");

            check.setString(1, acc);

            ResultSet rs = check.executeQuery();

            if (!rs.next()) {
                con.rollback();
                return false;
            }

            double balance = rs.getDouble("BALANCE");

            if (balance < amt) {
                con.rollback();
                return false;
            }

            // Update balance
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE ACCOUNT_NO=?");

            ps.setDouble(1, amt);
            ps.setString(2, acc);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                con.rollback();
                return false;
            }

            // Get updated balance
            PreparedStatement balPs = con.prepareStatement(
                    "SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO=?");

            balPs.setString(1, acc);

            ResultSet balRs = balPs.executeQuery();

            double balanceAfter = 0;

            if (balRs.next()) {
                balanceAfter = balRs.getDouble("BALANCE");
            }

            // Save transaction
            saveTransaction(
                    con,
                    acc,
                    "WITHDRAW",
                    amt,
                    balanceAfter);

            con.commit();

            return true;

        } catch (Exception e) {

            if (con != null) {
                con.rollback();
            }

            throw e;

        } finally {

            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }

        }
    }
    public boolean isValidAccount(String accountNo) {

        boolean valid = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql =
                    "SELECT ACCOUNT_NO FROM ACCOUNT WHERE ACCOUNT_NO=? AND STATUS='ACTIVE'";

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setString(1, accountNo);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                valid = true;

            }

            con.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

        return valid;

    }
    public boolean transferMoney(String fromAccount,
            String toAccount,
            double amount) {

Connection con = null;

boolean status = false;

try {

con = DBConnection.getConnection();

con.setAutoCommit(false);

// Check Sender Balance
String balanceSql =
"SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO=? AND STATUS='ACTIVE'";

PreparedStatement balancePs =
con.prepareStatement(balanceSql);

balancePs.setString(1, fromAccount);

ResultSet rs = balancePs.executeQuery();

if (!rs.next()) {

con.rollback();
return false;

}

double balance = rs.getDouble("BALANCE");

if (balance < amount) {

con.rollback();
return false;

}

// Debit Sender
String debitSql =
"UPDATE ACCOUNT SET BALANCE = BALANCE - ? WHERE ACCOUNT_NO=?";

PreparedStatement debitPs =
con.prepareStatement(debitSql);

debitPs.setDouble(1, amount);
debitPs.setString(2, fromAccount);

int debitRows = debitPs.executeUpdate();

if (debitRows == 0) {

con.rollback();
return false;

}

// Credit Receiver
String creditSql =
"UPDATE ACCOUNT SET BALANCE = BALANCE + ? WHERE ACCOUNT_NO=?";

PreparedStatement creditPs =
con.prepareStatement(creditSql);

creditPs.setDouble(1, amount);
creditPs.setString(2, toAccount);

int creditRows = creditPs.executeUpdate();

if (creditRows == 0) {

con.rollback();
return false;

}

// Get Updated Sender Balance
String senderBalanceSql =
"SELECT BALANCE FROM ACCOUNT WHERE ACCOUNT_NO=?";

PreparedStatement senderPs =
con.prepareStatement(senderBalanceSql);

senderPs.setString(1, fromAccount);

ResultSet senderRs =
senderPs.executeQuery();

double senderBalance = 0;

if (senderRs.next()) {

senderBalance =
   senderRs.getDouble("BALANCE");

}

// Get Updated Receiver Balance
PreparedStatement receiverPs =
con.prepareStatement(senderBalanceSql);

receiverPs.setString(1, toAccount);

ResultSet receiverRs =
receiverPs.executeQuery();

double receiverBalance = 0;

if (receiverRs.next()) {

receiverBalance =
   receiverRs.getDouble("BALANCE");

}

// Save Sender History
saveTransaction(con,
fromAccount,
"TRANSFER DEBIT",
amount,
senderBalance);

// Save Receiver History
saveTransaction(con,
toAccount,
"TRANSFER CREDIT",
amount,
receiverBalance);

con.commit();

status = true;

} catch (Exception e) {

try {

if (con != null) {

con.rollback();

}

} catch (Exception ex) {

ex.printStackTrace();

}

e.printStackTrace();

} finally {

try {

if (con != null) {

con.setAutoCommit(true);

con.close();

}

} catch (Exception e) {

e.printStackTrace();

}

}

return status;

}
    private void saveTransaction(Connection con,
            String accountNo,
            String type,
            double amount,
            double balanceAfter) throws Exception {

String sql =
"INSERT INTO TRANSACTION_HISTORY " +
"(TRANSACTION_ID, ACCOUNT_NO, TRANSACTION_TYPE, AMOUNT, BALANCE_AFTER, TRANSACTION_DATE) " +
"VALUES (TRANSACTION_SEQ.NEXTVAL, ?, ?, ?, ?, SYSDATE)";

PreparedStatement ps =
con.prepareStatement(sql);

ps.setString(1, accountNo);
ps.setString(2, type);
ps.setDouble(3, amount);
ps.setDouble(4, balanceAfter);

ps.executeUpdate();

}
    public Account getAccount(String acc) throws Exception {

        Connection con = DBConnection.getConnection();

        PreparedStatement ps = con.prepareStatement(
            "SELECT ACCOUNT_NO, ACCOUNT_HOLDER, BALANCE, STATUS, EMAIL, MOBILE, ADDRESS FROM ACCOUNT WHERE ACCOUNT_NO=?"
        );

        ps.setString(1, acc);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            Account a = new Account();
            a.setAccountNo(rs.getString("ACCOUNT_NO"));
            a.setAccountHolder(rs.getString("ACCOUNT_HOLDER"));
            a.setBalance(rs.getDouble("BALANCE"));
            a.setStatus(rs.getString("STATUS"));

            
            a.setEmail(rs.getString("EMAIL"));
            a.setMobile(rs.getString("MOBILE"));
            a.setAddress(rs.getString("ADDRESS"));
            return a;
        }

        return null;
    }
    public boolean updateProfile(String accountNo,
            String email,
            String mobile,
            String address) {

try {

Connection con = DBConnection.getConnection();

String sql =
"UPDATE ACCOUNT SET EMAIL=?, MOBILE=?, ADDRESS=? WHERE ACCOUNT_NO=?";

PreparedStatement ps =
con.prepareStatement(sql);

ps.setString(1, email);
ps.setString(2, mobile);
ps.setString(3, address);
ps.setString(4, accountNo);

int rows = ps.executeUpdate();

con.close();

return rows > 0;

} catch (Exception e) {

e.printStackTrace();

}

return false;

}
    public List<Transaction> getTransactionHistory(String accountNo) {

        List<Transaction> list = new ArrayList<>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            con = DBConnection.getConnection();

            String sql = "SELECT TRANSACTION_ID, ACCOUNT_NO, TRANSACTION_TYPE, "
                       + "AMOUNT, BALANCE_AFTER, TRANSACTION_DATE "
                       + "FROM TRANSACTION_HISTORY "
                       + "WHERE ACCOUNT_NO = ? "
                       + "ORDER BY TRANSACTION_DATE DESC";

            ps = con.prepareStatement(sql);
            ps.setString(1, accountNo);

            rs = ps.executeQuery();

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
        } finally {

            try {
                if (rs != null)
                    rs.close();
            } catch (Exception e) {
            }

            try {
                if (ps != null)
                    ps.close();
            } catch (Exception e) {
            }

            try {
                if (con != null)
                    con.close();
            } catch (Exception e) {
            }
        }

        return list;
    }
    public boolean checkCurrentPassword(String accountNo, String currentPassword) {

        boolean valid = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM ACCOUNT WHERE ACCOUNT_NO=? AND PASSWORD=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, accountNo);
            ps.setString(2, currentPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                valid = true;
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return valid;
    }
    public boolean changePassword(String accountNo, String newPassword) {

        boolean status = false;

        try {

            Connection con = DBConnection.getConnection();

            String sql = "UPDATE ACCOUNT SET PASSWORD=? WHERE ACCOUNT_NO=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, newPassword);
            ps.setString(2, accountNo);

            status = ps.executeUpdate() > 0;

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}