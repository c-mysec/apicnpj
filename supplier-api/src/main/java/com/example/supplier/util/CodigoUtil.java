package com.example.supplier.util;

public class CodigoUtil {

    public static boolean isValidCNPJ(String cnpj) {
        String cnpjStr = cnpj.toUpperCase();
        if (cnpjStr.length() != 14) {
            return false;
        }

        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                int value = Character.isDigit(cnpjStr.charAt(i)) ? Character.getNumericValue(cnpjStr.charAt(i)) :
                        (int) cnpjStr.charAt(i) - 48;
                sum += value * weight1[i];
            }

            int mod = sum % 11;
            char firstDigit = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            sum = 0;
            for (int i = 0; i < 13; i++) {
                int value = Character.isDigit(cnpjStr.charAt(i)) ? Character.getNumericValue(cnpjStr.charAt(i)) :
                        (int) cnpjStr.charAt(i) - 48;
                sum += value * weight2[i];
            }

            mod = sum % 11;
            char secondDigit = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            return cnpjStr.charAt(12) == firstDigit && cnpjStr.charAt(13) == secondDigit;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String cnpj = "12345678000195"; // Example CNPJ
        System.out.println("CNPJ is valid: " + isValidCNPJ(cnpj));
    }
}