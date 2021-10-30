package com.company;

import java.util.Objects;

public class LogIn {

    private final String cardNumber;
    private final String password;

    public LogIn(String cardNumber, String password) {
        this.cardNumber = cardNumber;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogIn logIn)) return false;
        return cardNumber.equals(logIn.cardNumber) &&
                password.equals(logIn.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber, password);
    }
}
