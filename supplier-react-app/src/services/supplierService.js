const serverUrl = 'https://sturdy-spork-v54qqr69q3pw95-8080.app.github.dev';
import { TextField } from '@material-ui/core';

export const getAllSuppliers = async () => {
    const response = await fetch(serverUrl + '/api/suppliers');
    if (!response.ok) {
        throw new Error('Failed to fetch suppliers');
    }
    return await response.json();
};

// Updated function to create a supplier with CNPJ cleaned up and uppercase letters
export const createSupplier = async (supplier) => {
  // Remove non-alphanumeric characters and convert cnpj string to uppercase
  const cleanedCnpj = supplier.cnpj.replace(/\W/g, '').toUpperCase();
  
  // Convert each character to its ASCII code minus 48 if it's a letter or number
  let asciiAdjustedCnpj = '';
  for (let i = 0; i < cleanedCnpj.length; i++) {
    const charCode = cleanedCnpj[i].charCodeAt(0);
    if (charCode >= 48 && charCode <= 57) asciiAdjustedCnpj += String.fromCharCode(charCode - 48); // numbers
    else if (charCode >= 65 && charCode <= 90) asciiAdjustedCnpj += String.fromCharCode(charCode + 17); // uppercase letters 'A' to 'Z'
  }
  
  const adjustedSupplier = { ...supplier, cnpj: asciiAdjustedCnpj };
  
  const response = await fetch(`${serverUrl}/api/suppliers`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(adjustedSupplier),
  });
  
  if (!response.ok) {
    throw new Error('Failed to create supplier');
  }
  
  return await response.json();
};