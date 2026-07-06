package com.banking.model;

public class Account {

    private String accountNo;
    private String accountHolder;
    private String password;
    private double balance;
    private String status;
    private String email;
    private String mobile;
    private String address;

    


	

	public Account() {
    }

    public Account(String accountNo,
                   String accountHolder,
                   String password,
                   double balance,
                   String status) {

        this.accountNo = accountNo;
        this.accountHolder = accountHolder;
        this.password = password;
        this.balance = balance;
        this.status = status;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(String accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}