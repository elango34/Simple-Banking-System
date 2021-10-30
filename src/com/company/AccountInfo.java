package com.company;

public class AccountInfo {

    private long balance;


    public AccountInfo(long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return balance;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountInfo that)) return false;
        return getBalance() == that.getBalance();
    }

}