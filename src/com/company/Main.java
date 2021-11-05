package com.company;
import java.util.*;


public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();

        String DBName = args[1];
        bank.DB_NAME = DBName;
        String Url = "jdbc:sqlite:" + DBName;
        bank.setURL(Url);

        bank.open();

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
                    String pin = scanner.next();
                    if (! bank.loggingIn(cardNumber, pin)) {
                        continue;
                    } else {
                        bank.close();
                        return;
                    }
                case 0:
                    bank.close();
                    System.out.println("\nBye!");
                    return;

            }
        }

    }


}