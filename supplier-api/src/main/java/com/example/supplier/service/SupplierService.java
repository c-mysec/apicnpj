package com.example.supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {
    private final CNPJValidator cnpjValidator = new CNPJValidator();
    
    @Autowired
    protected SupplierRepository supplierRepository;

    public Supplier createSupplier(String cnpj, String name) {
        if (!cnpjValidator.isValidCNPJ(cnpj)) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }
        
        return supplierRepository.saveNewRecord(new Supplier(name, cnpj));
    }
    
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
    
    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
   
    public Supplier updateSupplier(String cnpj, Long id) {
        if (!cnpjValidator.isValidCNPJ(cnpj)) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }
        
        return supplierRepository.findByIdAndUpdateCnpj(id, cnpj);
    }
    
    public boolean deleteSupplier(Long id) {
        return supplierRepository.deleteById(id);
    }
}

// CNPJValidator Class
package com.example.supplier;

public class CNPJValidator {

    private final static int[] weight = new int[]{5, 4, 3, 2, 9, 8, 7, 6, 4, 3};
    
    public boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || !cnpj.matches("[A-Z0-9]{14}")) {
            return false;
        }
        
        cnpj = cnpj.toUpperCase(); // convert to uppercase for validation

        try {
            String[] parts = {cnpj.substring(0, 12), cnpj.substring(12)};
            
            int sum = 0;
            for (int i = 0; i < weight.length - 1; i++) { // iterate over the first 12 digits and their weights
                int digitValue = Character.getNumericValue(parts[0].charAt(i)) - 48;
                sum += digitValue * weight[i];
            }
            
            int dv1 = (sum % 11) == 0 ? 0 : 11 - (sum % 11);
            String expectedDV2 = "" + ((dv1 < 2 ? '0' : '') + cnpj.substring(12, 13)) + dv1; // calculate the second DV using first 12 digits and compare it with the actual last two characters of CNPJ
            return parts[1].equals(""+ (dv1 < 2 ? '0' : '') + cnpj.substring(12, 13) + dv1); // validate second DV
            
        } catch (StringIndexOutOfBoundsException e){
            return false;
        }
    }
}