package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank {

    private Map<LogIn, AccountInfo> map = new HashMap<>();


    public void createAccount() {

        long lower = 1000000000L;
        long upper = 9999999999L;
        long temp = 4000000000000000L;
        long accountNumber;
        long cardNumber;
        int pin;
        AccountInfo accountInfo;
        LogIn logIn;


            accountNumber = (long) (Math.random() * (upper - lower + 1) + lower);

            cardNumber = temp + accountNumber;
            pin = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
            accountInfo = new AccountInfo(0);
            logIn = new LogIn(cardNumber, pin);
            map.putIfAbsent(logIn, accountInfo);

        System.out.println("Your card has been created");
        System.out.println("Your card number :" +"\n" + cardNumber);
        System.out.println("Your card PIN:" + "\n" + pin);
    }

    public boolean loggingIn(long cardNumber, int pin) {
        LogIn logIn = new LogIn(cardNumber, pin);
        Scanner scanner = new Scanner(System.in);

        if ( ! map.containsKey(logIn)) {
            System.out.println("Wrong card number or PIN!");
            return false;
        }
        System.out.println("You have successfully logged in!");

        while (true) {
            System.out.println("1. Balance" +"\n" + "2. Log out" +"\n" + "0. Exit");
            int get = scanner.nextInt();
            switch (get) {
                case 1 :
                    long bal = map.get(logIn).getBalance();
                    System.out.println("Balance: "+ bal);
                    break;
                case 2:
                    System.out.println("You have successfully logged out !");
                    return false;
                case 0:
                    return true;
            }
        }
    }





}
