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
        if (!(o instanceof AccountInfo)) return false;
        AccountInfo that = (AccountInfo) o;
        return getBalance() == that.getBalance();
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(getBalance());
//    }
}
