package com.company;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bank {

    private Map<LogIn, AccountInfo> map = new HashMap<>();


    public void createAccount() {
        int pin;
        AccountInfo accountInfo;
        LogIn logIn;
        String cardNumber = generateCardByLuhn();

        pin = (int) (Math.random() * (9999 - 1000 + 1) + 1000);
        accountInfo = new AccountInfo(0);
        logIn = new LogIn(cardNumber, pin);
        map.putIfAbsent(logIn, accountInfo);

        System.out.println("Your card has been created");
        System.out.println("Your card number :" +"\n" + cardNumber);
        System.out.println("Your card PIN:" + "\n" + pin);
    }

    public boolean loggingIn(String cardNumber, int pin) {
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


    public String generateCardByLuhn() {
        long lower = 100000000L;
        long upper = 999999999L;
        String bin = "400000";
        long accountNumber;
        String cardNumberWithoutCheckSum;
        //range
        accountNumber = (long) (Math.random() * (upper - lower + 1) + lower);
        cardNumberWithoutCheckSum = bin + accountNumber;


        int c;
        int sum = 0;

        for (int i = 0; i < cardNumberWithoutCheckSum.length(); i++) {
            c =  cardNumberWithoutCheckSum.charAt(i) - '0';
            if (i % 2 == 0) {
                c = c * 2;
                if (c > 9) {
                    c = c - 9;
                }
            }
            sum = sum + c;
        }
        String cardNumberWithCheckSum;
        if (sum % 10 == 0) {
            cardNumberWithCheckSum = cardNumberWithoutCheckSum + "0";
        } else {
            int a = 10 - (sum % 10);
            cardNumberWithCheckSum = cardNumberWithoutCheckSum + a;
        }

        return cardNumberWithCheckSum;

    }





}