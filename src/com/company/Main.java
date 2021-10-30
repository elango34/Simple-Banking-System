package com.company;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.*;


public class Main {
    public static void main(String[] args) {
        Bank bank = new Bank();

        String DBName = args[1];
        String table_name = "card";

        SQLiteDataSource dataSource = new SQLiteDataSource();
        String url = "jdbc:sqlite:"+ DBName;

        dataSource.setUrl(url);
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS " + table_name);
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + table_name
                    +"('id' INTEGER PRIMARY KEY, 'number' TEXT UNIQUE, 'pin' TEXT, 'balance' INTEGER DEFAULT 0)");

            statement.close();
            conn.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }



        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Create an account" + "\n" + "2. Log into account" + "\n" + "0. Exit");
            int a = scanner.nextInt();
            switch (a) {
                case 1:
                    bank.createAccount();
                    bank.insertAccount(table_name, url);
                    break;
                case 2:
                    System.out.println("Enter your card number :");
                    String cardNumber = scanner.next();
                    System.out.println("Enter your PIN :");
                    String pin = scanner.next();
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