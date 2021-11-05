package com.company;

import org.sqlite.SQLiteDataSource;
import java.sql.*;
import java.util.Scanner;

public class Bank {
    public String DB_NAME;
    private String URL;
    private static final String TABLE_CARD = "card";
    private static final String COLUMN_CARD_ID = "id";
    private static final String COLUMN_CARD_ACCOUNT = "number";
    private static final String COLUMN_CARD_PIN = "pin";
    private static final String COLUMN_CARD_BALANCE = "balance";

    private static final String QUERY_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CARD
            + "('" + COLUMN_CARD_ID + "' INTEGER PRIMARY KEY, '"
            + COLUMN_CARD_ACCOUNT + "' TEXT UNIQUE, '" + COLUMN_CARD_PIN + "' TEXT, '"
            + COLUMN_CARD_BALANCE + "' INTEGER DEFAULT 0)";


    private static final String QUERY_INSERT = "INSERT INTO " + TABLE_CARD + " VALUES(?, ?, ?, ?)";
    private static final String QUERY_CARD = "SELECT * FROM " + TABLE_CARD + " WHERE " + COLUMN_CARD_ACCOUNT + " = ? AND "
            + COLUMN_CARD_PIN + " = ?";
    private static final String QUERY_BALANCE = " SELECT " + COLUMN_CARD_BALANCE + " FROM " + TABLE_CARD + " WHERE " + COLUMN_CARD_ACCOUNT + " = ?";
    private static final String QUERY_ADD_INCOME = "UPDATE " + TABLE_CARD + " SET " + COLUMN_CARD_BALANCE + " = ? WHERE " + COLUMN_CARD_ACCOUNT + " = ?";
    private static final String QUERY_RECEIVER_CARD = "SELECT * FROM " + TABLE_CARD + " WHERE " + COLUMN_CARD_ACCOUNT + " = ?";
    private static final String QUERY_DELETE_ACCOUNT = "DELETE FROM " + TABLE_CARD + " WHERE " + COLUMN_CARD_ACCOUNT + " = ?";

    private final SQLiteDataSource dataSource = new SQLiteDataSource();
    private Connection conn = null;
    private PreparedStatement insertCard;
    private PreparedStatement queryCard;
    private PreparedStatement queryBalance;
    private PreparedStatement addIncome;
    private PreparedStatement queryReceiverCard;
    private PreparedStatement deleteAccount;

    private final Card card = new Card();

    public void setURL(String Url) {
        this.URL = Url;
    }

    public void open() {
        try {
            dataSource.setUrl(URL);
            conn = dataSource.getConnection();

            Statement statement = conn.createStatement();
            statement.executeUpdate(QUERY_CREATE_TABLE);

            insertCard = conn.prepareStatement(QUERY_INSERT);
            queryCard = conn.prepareStatement(QUERY_CARD);
            queryBalance = conn.prepareStatement(QUERY_BALANCE);
            addIncome = conn.prepareStatement(QUERY_ADD_INCOME);
            queryReceiverCard = conn.prepareStatement(QUERY_RECEIVER_CARD);
            deleteAccount = conn.prepareStatement(QUERY_DELETE_ACCOUNT);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void close() {
        try {

            if (insertCard != null) {
                insertCard.close();
            }
            if (queryCard != null) {
                queryCard.close();
            }
            if (queryBalance != null) {
                queryBalance.close();
            }
            if (queryReceiverCard != null) {
                queryReceiverCard.close();
            }
            if (addIncome != null) {
                addIncome.close();
            }
            if (deleteAccount != null) {
                deleteAccount.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean loggingIn(String cardNumber, String pin) {
        Scanner scanner = new Scanner(System.in);
        try {
            if (!checkCardExist(cardNumber, pin)) {
                System.out.println("\nWrong card number or PIN!");
                return false;
            }
            System.out.println("\nYou have successfully logged in! ");

            while (true) {
                System.out.println("\n1. Balance" + "\n" + "2. Add income" + "\n"
                        + "3. Do transfer" + "\n" + "4. Close account" + "\n" + "5. Log out" + "\n" + "0. Exit");

                int get = scanner.nextInt();
                switch (get) {
                    case 1:
                        int bal = getBalance(cardNumber);
                        System.out.println("\n Balance: " + bal);
                        break;
                    case 2:
                        System.out.println("\nEnter income:");
                        addIncome(cardNumber, scanner.nextInt());
                        System.out.println("Income was added! ");
                        break;
                    case 3:
                        System.out.println("Transfer");
                        System.out.println("Enter card number:");
                        String receiver = scanner.next();
                        if (checkReceiverCard(cardNumber, receiver)) {
                            if (transfer(cardNumber, receiver)) {
                                System.out.println("Success!");
                            }
                        }
                        break;
                    case 4:
                        deleteAccount(cardNumber);
                        System.out.println("The account has been closed! \n");
                        return false;
                    case 5:
                        System.out.println("You have successfully logged out !");
                        return false;
                    case 0:
                        return true;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }


    public void createAccount() {
        String cardNumber;
        String pin;

        try {
            while (true) {
                cardNumber = card.generateCardByLuhn();
                pin = (int) (Math.random() * (9999 - 1000 + 1) + 1000) + "";

                queryReceiverCard.setString(1, cardNumber);
                ResultSet resultSet = queryReceiverCard.executeQuery();
                if (!resultSet.next()) {
                    break;
                }
            }
            insertCard.setNull(1, Types.VARCHAR);
            insertCard.setString(2, cardNumber);
            insertCard.setString(3, pin);
            insertCard.setDouble(4, 0);
            insertCard.executeUpdate();
            System.out.println("\nYour card has been created");
            System.out.println("Your card number:" + "\n" + cardNumber);
            System.out.println("Your card PIN:" + "\n" + pin + "\n");


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    private boolean checkCardExist(String cardNumber, String pin) throws SQLException {

        queryCard.setString(1, cardNumber);
        queryCard.setString(2, pin);
        ResultSet result = queryCard.executeQuery();
        if (!result.next()) {
            return false;
        }
        return true;
    }

    private int getBalance(String cardNumber) throws SQLException {

        queryBalance.setString(1, cardNumber);
        ResultSet result = queryBalance.executeQuery();
        if (result.next()) {
            return result.getInt(COLUMN_CARD_BALANCE);
        }
        return 0;
    }

    private void addIncome(String cardNumber, int income) throws SQLException {
        int balance = getBalance(cardNumber);

        addIncome.setDouble(1, balance + income);
        addIncome.setString(2, cardNumber);
        addIncome.executeUpdate();

    }

    private void debitAmount(String cardNumber, int amount) throws SQLException {
        int balance = getBalance(cardNumber);

        addIncome.setDouble(1, balance - amount);
        addIncome.setString(2, cardNumber);
        addIncome.executeUpdate();

    }

    private void deleteAccount(String cardNumber) throws SQLException {

        deleteAccount.setString(1, cardNumber);
        deleteAccount.executeUpdate();

    }


    private boolean receiverCardExists(String cardNumber) throws SQLException {
        queryReceiverCard.setString(1, cardNumber);
        ResultSet result = queryReceiverCard.executeQuery();
        return result.next();
    }

    private boolean checkReceiverCard(String cardNumber, String receiver) throws SQLException {

        if (cardNumber.equals(receiver)) {
            System.out.println("You can't transfer money to the same account!");
            return false;
        }

        if (!card.validateCardNumberBYLuhn(receiver)) {
            System.out.println("Probably you made a mistake in the card number \nPlease try again!");
            return false;
        }

        if (!receiverCardExists(receiver)) {
            System.out.println("Such a card does not exist ");
            return false;
        }
        return true;
    }

    private boolean transfer(String cardNumber, String receiver) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter how much money you want to transfer:");
        try {
            int amount = scanner.nextInt();
            int balance = getBalance(cardNumber);
            if (amount > balance) {
                System.out.println("Not enough money!");
                return false;
            }
            int oldTotal = getBalance(cardNumber) + getBalance(receiver);

            conn.setAutoCommit(false);
            debitAmount(cardNumber, amount);
            addIncome(receiver, amount);

            int newTotal = getBalance(cardNumber) + getBalance(receiver);
            if (oldTotal == newTotal) {
                conn.commit();
            } else {
                throw new SQLException("couldn't add amount");
            }

        } catch (Exception e) {
            try {
                System.out.println("performing roll back");
                System.out.println(e.getMessage());
                conn.rollback();
                return false;
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        } finally {
            try {
//                System.out.println("setting auto commit to true");
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return true;
    }


}