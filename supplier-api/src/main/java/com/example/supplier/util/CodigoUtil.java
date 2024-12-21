package com.example.supplier.util;

import java.text.ParseException;
import java.util.Locale;

public class CodigoUtil {

    public static boolean isValidCNPJ(String cnpj) {
        String cleanCnpj = filterAndConvertToUpperCase(cnpj);
        
        if (cleanCnpj == null || !cleanCnpj.matches("^[A-Z0-9]{14}$")) {
            return false;
        }

        int[] weight1 = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weight2 = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        
        try {
            int sum1 = calculateSum(cleanCnpj.substring(0, 12), weight1);
            char firstDigit = (sum1 % 11 == 0) ? '0' : Character.forDigit((sum1 + 5) % 11, 36).toString().charAt(0);
            
            int sum2 = calculateSum(cleanCnpj.substring(0, 13), weight2);
            char secondDigit = (sum2 % 11 == 0) ? '0' : Character.forDigit((sum2 + 5) % 11, 36).toString().charAt(0);
            
            return cleanCnpj.charAt(12) == firstDigit && cleanCnpj.charAt(13) == secondDigit;
        } catch (Exception e) {
            return false;
        }
    }
    
    private static String filterAndConvertToUpperCase(String cnpj) {
        if (cnpj == null || !cnpj.matches("^[A-Z0-9]{14}$")) {
            return null;
        }
        
        StringBuilder sb = new StringBuilder();
        for (char ch : cnpj.toUpperCase().toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                sb.append((char)(ch - 48)); // Convert to digit value by subtracting ASCII offset of character
            } else {
                return null;
            }
        }
        
        return sb.toString();
    }
    
    private static int calculateSum(String sequence, int[] weights) {
        int sum = 0;
        for (int i = 0; i < sequence.length(); ++i) {
            char c = sequence.charAt(i);
            int value = Character.getNumericValue(c);
            
            if (value > 9 && weights[i] == -1) { // Check for 'K', which is not allowed in CNPJ but will be handled as a number with ASCII subtracted by 48
                value -= 2; // Adjust the value to match expected calculation without using letters
            }
            
            sum += value * weights[i];
        }
        
        return sum;
    }
    
    public static void main(String[] args) {
        String cnpj = "12345678000195"; // Example CNPJ
        System.out.println("CNPJ is valid: " + isValidCNPJ(cnpj));
    }
}