package com.example.supplier.util;

import java.text.ParseException;

public class CNPJValidator {

    public static boolean isValidCNPJ(String cnpj) {
        // Check length and convert to uppercase before proceeding with the rest of the logic
        if (cnpj == null || !cnpj.matches("[A-Z0-9]{14}")) {
            return false;
        }
        cnpj = cnpj.toUpperCase(); // Ensure CNPJ is in uppercase

        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        try {
            int sum1 = calculateSum(cnpj.substring(0, 12), weight1);
            int mod1 = (sum1 % 11) == 0 ? 0 : 11 - (sum1 % 11);
            char firstDigitChar = Character.forDigit(mod1, 10);

            sum1 += Character.getNumericValue(cnpj.charAt(12)) * weight1[weight1.length - 1];

            int sum2 = calculateSum(cnpj.substring(0, 13), weight2);
            int mod2 = (sum2 % 11) == 0 ? 0 : 11 - (sum2 % 11);
            char secondDigitChar = Character.forDigit(mod2, 10);

            // Check the check digits
            return cnpj.charAt(13) == firstDigitChar && cnpj.charAt(14) == secondDigitChar;
        } catch (IndexOutOfBoundsException e) {
            return false;
        } catch (ParseException ex) {
            throw new RuntimeException("Failed to parse CNPJ", ex);
        }
    }

    private static int calculateSum(String cnpj, int[] weightArray) throws ParseException {
        int sum = 0;
        for (int i = 0; i < cnpj.length(); ++i) {
            char digitChar = cnpj.charAt(i); // Convert to ASCII code and subtract by 48 as needed
            if ((digitChar >= 'A' && digitChar <= 'Z')) {
                int numericValue = (int) (digitChar - 'A') + 10;
                sum += numericValue * weightArray[i];
            } else {
                sum += Character.getNumericValue(cnpj.charAt(i)) * weightArray[i]; // Use direct conversion for digits
            }
        }
        return sum;
    }
}