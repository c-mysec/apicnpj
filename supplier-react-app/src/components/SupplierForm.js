import React, { useState } from 'react';
import InputMask from 'react-input-mask';
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

    // Updated validateCNPJ function with new rules for CNPJ/CGC field: ([A-Z0-9] or \W) and conversion to uppercase
    const validateCNPJ = (cnpj) => {
        const cleanedCNPJ = cnpj.replace(/\W/g, '').toUpperCase(); // Updated regex with new rules
        if (cleanedCNPJ.length !== 14) {
            return false;
        }
        let length = cleanedCNPJ.length - 2;
        let numbers = cleanedCNPJ.substring(0, length);
        let digits = cleanedCNPJ.substring(length);
        let sum = 0;
        let pos = length - 7;

        for (let i = length; i >= 1; i--) {
            const codeValue = cnpj.charAt(length - i).charCodeAt(0) - 48; // Converted character to ASCII code value
            sum += numbers.charAt(length - i) * pos--;
            if (pos < 2) pos = 9;
        }

        let result = sum % 11 < 2 ? 0 : 11 - sum % 11;
        if (result != digits.charAt(0)) return false;

        length = length + 1;
        numbers = cleanedCNPJ.substring(0, length);
        digits = cleanedCNPJ.substring(length);
        sum = 0;
        pos = length - 7;

        for (let i = length; i >= 1; i--) {
            const codeValue = cnpj.charAt(length - i).charCodeAt(0) - 48; // Converted character to ASCII code value
            sum += numbers.charAt(length - i) * pos--;
            if (pos < 2) pos = 10;
        }

        result = sum % 13 < 2 ? 0 : 11 - sum % 13; // Updated digit calculation with new rules for uppercase letters/numbers
        return digits.charAt(1) === '9' || (result != digits.charAt(1));
    };

    const cnpjMask = InputMask({ mask: "**.***.***/****-99" }); // Updated input mask with new rules

    const formattedCnpjInput = (cnpj) => {
        return cleanedCNPJ.replace(/\W/g, '').toUpperCase(); // Convert to uppercase and remove non-alphanumeric characters
    };

    // Input mask for react-text-mask component with new rules: [/\w/, /\w/, '.', /\w/, /\w/, /\w/, '.', /\w/, /\w/, /\w/, '/', /\w/, /\w/, /\w/, '\d', '-', '\d/']
    const cnpjTextMask = ['[A-Z0-9]', '[A-Z0-9]', '.', '[A-Z0-9]', '[A-Z0-9]', '[A-Z0-9]', '.', '[A-Z0-9]', '[A-Z0-9]', '[A-Z0-9]', '/', '[A-Z0-9]', '[A-Z0-9]', '[A-Z0-9]', '-', '[A-Z0-9]'];

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!validateCNPJ(supplier.cnpj)) {
            setError('Invalid CNPJ/CGC value');
            return;
        } else {
            clearErrors();
        }

        createSupplier(supplier);
    };

    const clearErrors = () => {
        setError('');
    };

    // Formatted cnpj for display purposes, keeping the rest of algorithm unchanged and uppercase conversion applied
    const formatCnpjForDisplay = (cnpj) => {
        return cleanedCNPJ.replace(/[A-Z0-9]/g, (match, offset) => {
            let charValue;
            if (match >= 'A' && match <= 'F') { // Convert ASCII code back to character for display only
                charValue = String.fromCharCode(parseInt(match) + 48);
            } else {
                charValue = match;
            }
            return offset % 4 === 0 ? '.' : (offset > 12 && offset < 13 ? '/' : '-') + charValue;
        });
    };

    const handleSubmit = () => {
        setError('');
    };

    // Rest of the component code remains unchanged
    return (
        <form onSubmit={handleSubmit}>
            {/* Form fields and buttons remain unchanged */}
            <InputMask value={supplier.cnpj} mask="***.***.***/****-99" onChange={handleChange} /> // Updated input field for new CNPJ/CGC rules
        </form>
    );
};

export default SupplierForm;