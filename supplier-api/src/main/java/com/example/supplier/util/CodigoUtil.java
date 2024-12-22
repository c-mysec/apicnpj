    package com.example.supplier.util;
    
import javax.swing.*;
    
/**
 * The {@link CNPJValidator} class provides methods to validate and format Brazilian CNPJs, taking into account that they can be in uppercase letters or numbers.
 */
public class CNPJValidator {
    
    /**
     * Validates a given CNPJ string by applying the necessary rules and checking its check digits.
     * 
     * @param cnpjStr The CNPJ string to validate (uppercase required).
     * @return {@code true} if the CNPJ is valid, otherwise {@code false}.
     */
    public static boolean isValidCNPJ(String cnpjStr) {
        if (!cnpjStr.matches("[A-Z0-9]{14}")) { // Ensure input contains only uppercase letters and numbers
            return false;
        }
        
        long cnpj = Long.parseLong(new StringBuffer(cnpjStr).reverse().toString(), 36);

        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        try {
            int sum1 = calculateCheckDigitSum(cnpj, weight1);
            char dv1 = (sum1 % 11 < 2) ? '0' : (char) ((11 - sum1 % 11) + 'A'); // Using 'A' for values >= 10
            
            int[] filteredCnpjDigits = new int[cnpjStr.length()];
            for(int i = 0; i < cnpjStr.length(); i++) {
                char ch = Character.toUpperCase(cnpjStr.charAt(i));
                if (ch >= 'A' && ch <= 'Z') {
                    filteredCnpjDigits[i] = ch - 'A' + 10; // Convert uppercase letters to their numeric values by subtracting ASCII code of 'A' and adding 10.
                } else if (ch >= '0' && ch <= '9') {
                    filteredCnpjDigits[e] = Character.getNumericValue(cnpjStr.charAt(i)); // Keep numbers as they are.
                } else {
                    return false;
                }
            }
            
            int sum2 = calculateCheckDigitSum(filteredCnpjDigits, weight2);
            char dv2 = (sum2 % 11 < 2) ? '0' : (char) ((11 - sum2 % 11) + 'A'); // Using 'A' for values >= 10
            
            return Character.toUpperCase(cnpjStr.charAt(12)) == dv1 && Character.toUpperCase(cnpjStr.charAt(13)) == dv2;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static int calculateCheckDigitSum(int[] cnpjDigits, int[] weights) {
        int sum = 0;
        for (int i = 0; i < cnpjDigits.length - 2; i++) { // Exclude the last two digits as they are check digits to be calculated
            sum += cnpjDigits[i] * weights[i];
        }
        
        return sum % 11;
    }
    
    /**
     * The main method for demonstrating CNPJ validation.
     */
    public static void main(String[] args) {
        String cnpj = "12345678000195"; // Example CNPJ in uppercase letters or numbers only (no special characters).
        System.out.println("CNPJ is valid: " + isValidCNPJ(cnpj));
    }
}