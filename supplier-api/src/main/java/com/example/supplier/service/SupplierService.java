package com.example.supplier.service;

import com.example.supplier.model.Supplier;
import com.example.supplier.repository.SupplierRepository;
import com.example.supplier.util.CodigoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    public Supplier createSupplier(Supplier supplier) {
        if (!CodigoUtil.isValidCNPJ(supplier.getCnpj())) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }
        supplier.setCnpj(supplier.getCnpj().toUpperCase());
        return supplierRepository.save(supplier);
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Optional<Supplier> getSupplierById(Long id) {
        return supplierRepository.findById(id);
    }

    public Supplier updateSupplier(Long id, Supplier supplierDetails) {
        if (!CodigoUtil.isValidCNPJ(supplierDetails.getCnpj())) {
            throw new IllegalArgumentException("Invalid CNPJ");
        }
        supplierDetails.setCnpj(supplierDetails.getCnpj().toUpperCase());
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id " + id));
        supplier.setNome(supplierDetails.getNome());
        supplier.setCnpj(supplierDetails.getCnpj());
        supplier.setNomeContato(supplierDetails.getNomeContato());
        supplier.setEmailContato(supplierDetails.getEmailContato());
        supplier.setTelefoneContato(supplierDetails.getTelefoneContato());
        return supplierRepository.save(supplier);
    }

    public boolean deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found with id " + id));
        supplierRepository.deleteById(id);
        return true;
    }
}