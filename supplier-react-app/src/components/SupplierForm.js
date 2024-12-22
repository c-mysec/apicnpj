import React, { useState } from 'react';
import InputMask from 'react-input-mask';
import TextInput from './TextInput'; // Assuming a custom TextInput component with input mask functionality is created elsewhere in the project.
import { createSupplier } from '../services/supplierService';

const SupplierForm = () => {
    const [supplier, setSupplier] = useState({
        nome: '',
        cnpj: '',
        nomeContato: '',
        emailContato: '',
        telefoneContato: ''
    });

    const [error, setError] = useState('');

    const handleChange = (e) => {
        const { name, value } = e.target;
        setSupplier({ ...supplier, [name]: value });
    };

    const validateCNPJ = (cnpj) => {
        // Remove non-numeric characters and convert to uppercase
        let cleanedCNPJ = cnpj.replace(/[^A-Z0-9]/gi, '').toUpperCase();
        
        if (cleanedCNPJ.length !== 14) return false;

        // Add your custom validation logic for the 2-digit code here
        let length = cleanedCNPJ.length - 2;
        let numbers = cleanedCNPJ.substring(0, length);
        let digits = cleanedCNPJ.substring(length);
        let sum = 0;
        let pos = length - 7;

        for (let i = length; i >= 1; i--) {
            sum += numbers.charAt(length - i) * pos--;
            if (pos < 2) pos = 9;
        }

        let result = sum % 11 < 2 ? 0 : 11 - sum % 11;
        if (result != digits.charAt(0)) return false;

        length = length + 1;
        numbers = cleanedCNPJ.substring(0, length);
        sum = 0;
        pos = length - 7;

        for (let i = length; i >= 1; i--) {
            sum += parseInt(numbers.charAt(length - i), 32) * pos--; // Parse each character as its ASCII code subtracted by 48
            if (pos < 2) pos = 9;
        }

        result = sum % 10 < 2 ? 0 : 11 - sum % 10;
        if (result != digits.charAt(1)) return false;
        
        return true; // Return true if CNPJ is valid, otherwise the calling function should handle validation errors
    };

    const checkCNPJ = () => {
        let cnpj = supplier.cnpj.replace(/[^A-Z0-9]/gi, '').toUpperCase(); // Use a custom TextInput component with input mask functionality elsewhere in the project to enforce this behavior automatically on user input
        
        if (!validateCNPJ(cnpj)) {
            setError('Invalid CNPJ/CGC. Please enter an uppercase alphanumeric value.');
            return false;
        } else {
            setError('');
            return true;
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        
        if (!checkCNPJ()) return;

        // Proceed with form submission logic here
        createSupplier(supplier).then(() => {
            setError('');
            // Handle successful creation of supplier, etc...
        }).catch((error) => {
            setError(`Failed to create supplier: ${error}`);
        });
    };

    return (
        <form onSubmit={handleSubmit}>
            <TextInput type="text" name="nome" value={supplier.nome} onChange={handleChange} required />
            {/* cnpj input with custom TextInput and mask */}
            <TextInput 
                type="text" 
                name="cnpj" 
                value={supplier.cnpj} 
                onChange={(value) => handleChange({ target: { name: 'cnpj', value } })} 
                mask="**.***.***/****-99" 
                parseFunc={() => cleanedCNPJ.replace(/[^A-Z0-9]/gi, '')} // Parse function for the mask
                inputMask={[/\w/, /\w/, '.', /\w/, /\w/, /\w/, '.', /\w/, /\w/, /\w/, '/', /\w/, /\w/, /\w/, /\w/, '-', /\d/, /\d/]} 
            />
            {/* Other inputs... */}
            <button type="submit">Create Supplier</button>
        </form>
    );
};

export default SupplierForm;
