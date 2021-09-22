package com.company;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        while (true) {
            System.out.println("1. Create an account" + "\n" + "2. Log into account" + "\n" + "0. Exit");
            int a = scanner.nextInt();
            switch (a) {
                case 1:
                    bank.createAccount();
                    break;
                case 2:
                    System.out.println("Enter your card number :");
                    String cardNumber = scanner.next();
                    System.out.println("Enter your PIN :");
                    int pin = scanner.nextInt();
                    if (! bank.loggingIn(cardNumber, pin)) {
                        continue;
                    } else {
                        return;
                    }
                case 0:
                    return;


            }
        }

    }


}