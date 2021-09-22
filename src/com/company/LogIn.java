package com.company;

import java.util.Objects;

public class LogIn {

    private long cardNumber;
    private int password;

    public LogIn(long cardNumber, int password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogIn)) return false;
        LogIn logIn = (LogIn) o;
        return cardNumber == logIn.cardNumber &&
                password == logIn.password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, password);
    }
}