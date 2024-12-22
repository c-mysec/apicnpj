const serverUrl = 'https://sturdy-spork-v54qqr69q3pw95-8080.app.github.dev';
import { MaskedInput, TextMask } from "react-input-mask";

export const getAllSuppliers = async () => {
  const response = await fetch(serverUrl+'/api/suppliers');
  if (!response.ok) {
      throw new Error('Failed to fetch suppliers');
  }
  return await response.json();
};

export const createSupplier = async (supplier) => {
    // Remove non-numeric and non-alphabetic characters from CNPJ
    const cleanedSupplier = {
        ...supplier,
        cnpj: supplier.cnpj.replace(/[^A-Z0-9]/ig, '')
    };

    // Convert the first four groups of two digits to uppercase (remove any separators)
    const cleanedCnpj = cleanedSupplier.cnpj.split(/\d{4}/g).map((group, index) => {
      if (index < 3 && group.length === 2) return group.toUpperCase();
      return group;
    }).join('');

    // Calculate the Check Digit (DV) with ASCII conversion: ('0'=0, '1'=1, '2'=2, 'A'=17, 'B'=18, 'C'=19, etc.)
    const cleanedWithDigit = generateCNPCDigits(cleanedCnpj); // Assumes this function already exists in your codebase.
    
    const response = await fetch(`${serverUrl}/api/suppliers`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(cleanedWithDigit),
    });

    if (!response.ok) {
      throw new Error('Failed to create supplier');
    }
    
    return await response.json();
};