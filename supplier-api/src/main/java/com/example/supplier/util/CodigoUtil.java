package com.example.supplier.util;

public class CodigoUtil {

    public static boolean isValidCNPJ(String cnpj) {
        cnpj = cnpj.toUpperCase();
        cnpj = cnpj.replaceAll("[^A-Z0-9]", "");

        if (cnpj.length() != 14) {
            return false;
        }

        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int sum = 0;
            for (int i = 0; i < 12; i++) {
                int value = cnpj.charAt(i) - 48;
                sum += value * weight1[i];
            }

            int mod = sum % 11;
            char firstDigit = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            sum = 0;
            for (int i = 0; i < 13; i++) {
                int value = cnpj.charAt(i) - 48;
                sum += value * weight2[i];
            }

            mod = sum % 11;
            char secondDigit = (mod < 2) ? '0' : (char) ((11 - mod) + '0');

            return cnpj.charAt(12) == firstDigit && cnpj.charAt(13) == secondDigit;
        } catch (Exception e) {
            return false;
        }
    }

    public static void main(String[] args) {
        String cnpj = "12345678000195"; // Example CNPJ
        System.out.println("CNPJ is valid: " + isValidCNPJ(cnpj));
    }
}