package com.company;


import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
//        char a = 4 + '0';

//        System.out.println(a);
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
                    long cardNumber = scanner.nextLong();
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