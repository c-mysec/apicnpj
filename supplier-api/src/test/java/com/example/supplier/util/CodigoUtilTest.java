package com.example.supplier.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CodigoUtilTest {

    @Test
    void isValidCNPJ_WithValidCNPJ_ShouldReturnTrue() {
        assertTrue(CodigoUtil.isValidCNPJ("12.345.678/0001-95".toUpperCase()), "Valid CNPJ should return true");
    }

    @Test
    void isValidCNPJ_WithInvalidCNPJ_ShouldReturnFalse() {
        assertFalse(CodigoUtil.isValidCNPJ("12.345.678/0001-96".toUpperCase()), "Invalid CNPJ should return false");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678000195", "12.345.678/0001-95", "12.345.678.0001-95"})
    void isValidCNPJ_WithValidFormats_ShouldReturnTrue(String cnpj) {
        assertTrue(CodigoUtil.isValidCNPJ(cnpj.toUpperCase()), "Valid CNPJ in different formats should return true");
    }

    @Test
    void isValidCNPJ_WithInvalidLength_ShouldReturnFalse() {
        assertFalse(CodigoUtil.isValidCNPJ("123456780001".toUpperCase()), "CNPJ with invalid length should return false");
    }

    @Test
    void isValidCNPJ_WithNonNumericCharacters_ShouldReturnFalse() {
        assertFalse(CodigoUtil.isValidCNPJ("12.345.67A/0001-95".toUpperCase()), "CNPJ with non-numeric characters should return false");
    }

    @Test
    void isValidCNPJ_WithAllZeros_ShouldReturnFalse() {
        assertFalse(CodigoUtil.isValidCNPJ("00000000000000".toUpperCase()), "CNPJ with all zeros should return false");
    }

    @Test
    void isValidCNPJ_WithNullInput_ShouldReturnFalse() {
        assertFalse(CodigoUtil.isValidCNPJ(null), "Null input should return false");
    }

    @Test
    void isValidCNPJ_WithEmptyString_ShouldReturnFalse() {
        assertFalse(CodigoUtil.isValidCNPJ(""), "Empty string should return false");
    }

    @ParameterizedTest
    @ValueSource(strings = {"11.111.111/1111-11", "22.222.222/2222-22", "33.333.333/3333-33"})
    void isValidCNPJ_WithRepeatedDigits_ShouldReturnFalse(String cnpj) {
        assertFalse(CodigoUtil.isValidCNPJ(cnpj.toUpperCase()), "CNPJ with repeated digits should return false");
    }

    @Test
    void isValidCNPJ_WithLeadingAndTrailingSpaces_ShouldReturnTrue() {
        assertTrue(CodigoUtil.isValidCNPJ("  12.345.678/0001-95  ".toUpperCase()), "CNPJ with leading and trailing spaces should return true");
    }

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isEmpty()) {
            return false;
        }
        cnpj = cnpj.replaceAll("[^A-Z0-9]", "").toUpperCase();
        if (cnpj.length() != 14) {
            return false;
        }
        int[] digits = new int[14];
        for (int i = 0; i < 14; i++) {
            digits[i] = cnpj.charAt(i) - 48;
        }
        int sum = 0;
        int factor = 5;
        for (int i = 0; i < 12; i++) {
            sum += digits[i] * factor;
            factor = (factor == 2) ? 9 : factor - 1;
        }
        int remainder = sum % 11;
        int checkDigit = (remainder < 2) ? 0 : 11 - remainder;
        return digits[12] == checkDigit;
    }
}