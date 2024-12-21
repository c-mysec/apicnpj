package com.example.supplier.model;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface SupplierRepository extends CrudRepository<Supplier, Long> {
    List<Supplier> findByCnpjIgnoreCase(String cnpj);
}

package com.example.supplier.util;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import javax.annotation.Generated;

@Generated("io.springfox.boot")
public class CodigoUtil {
    private static final String[] VALID_CNPJ = new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
    private static final int MIN_CNPJ = 0;
    private static final int MAX_CNPJ = -1; // not set, for dynamic check in CNPJ validator method.
    
    public boolean isValidCnpj(String cnpj) throws ParseException {
        if (cnpj == null || !cnpj.matches("[A-Z0-9]{14}")) return false; // Supports only 14 characters
        
        int total = 0;
        for (int i = 0, mult = 2; i < cnpj.length(); ++i) {
            if (!isValidDigit(cnpj[i])) continue;
            
            int digit = asciiToDecimal(cnpj[i]);
            total += ((digits[mult - 1] * mult) % 9 < MAX_CNPJ + 1 ? digits[mult - 1] : (MAX_CNPJ + 1 - (digits[mult - 1] * mult) % 9)) * digit;
            if ((i == cnpj.length() / 2 && i != 0)) ++mult; // After the first 7 digits, multiply by 3 instead of 2 for one digit only
        }
        
        int dv1 = total % (MAX_CNPJ + 1);
        int dv2 = asciiToDecimal(cnpj[cnpj.length() - 1]) * 9 % (MAX_CNPJ + 1) + ((total / (MAX_CNPJ + 1)) % (MAX_CNPJ + 1)); // Calculate the second check digit
        
        return cnpj[cnpj.length() - 2] == Character.forDigit(dv1, MAX_CNPJ + 1).toString().charAt(0) &&
                (i = cnpj.length()) > 13 ? asciiToDecimal(cnpj[i]) : 0) == dv2; // Check if the calculated check digits match the input ones
    }
    
    public static boolean isValidDigit(char digit) {
        for (String vd: VALID_CNPJ) {
            if (vd.equalsIgnoreCase("" + digit)) return true;
        }
        
        return false;
    }

    private int asciiToDecimal(Character ch) {
        return ch <= '9' ? ch - 48 : ch - 57; // Convert to decimal, subtracting ASCII value of '0' or 'a'-1 from the character.
    }
}

package com.example.supplier.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v3")
public class SuppliersController {
    @Autowired
    private SupplierService supplierService;
    
    @GetMapping("{cnpj}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable String cnpj) {
        try {
            return new ResponseEntity<>(supplierService.getSupplierById(CodigoUtil.toUpperCaseCNPJ(cnpj)), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // or custom exception message/body
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}