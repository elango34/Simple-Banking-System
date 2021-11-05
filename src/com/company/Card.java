package com.company;

public class Card {

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
            c = cardNumberWithoutCheckSum.charAt(i) - '0';
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

    public boolean validateCardNumberBYLuhn(String cardNumber) {
        String[] strings = cardNumber.split("");
        int[] ints = new int[strings.length];
        for (int i = 0; i < ints.length; i++) {
            ints[i] = Integer.parseInt(strings[i]);
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int number = ints[i] * 2;
            if (number > 9) {
                number = (number - 10) + 1;
            }
            ints[i] = number;
        }
        int sum = 0;
        for (int a : ints) {
            sum = sum + a;
        }
        return sum % 10 == 0;
    }
}
