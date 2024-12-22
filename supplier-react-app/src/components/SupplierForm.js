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

    const validateCNPJ = (cnpj) => {
        // Convert the CNPJ to uppercase and clean up non-alphanumeric characters
        const cleanedCNPJ = cnpj.toUpperCase().replace(/\W|[A-Z0-9]/g, '');
        
        // Check if CNPJ has 14 digits
        if (cleanedCNPJ.length !== 14) {
            return false;
        }

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
            sum += numbers.charAt(length - i) * pos--;
            if (pos < 2) pos = 9;
        }

        result = sum % 10 < 2 ? 0 : 11 - sum % 10;
        if (result != digits.charAt(1)) return false;

        return true;
    };

    const calculateCheckDigits = (cnpj) => {
        // Convert the CNPJ to uppercase and clean up non-alphanumeric characters
        const formattedCNPJ = cnpj.toUpperCase().replace(/\W|[A-Z0-9]/g, '');
        return formattedCNPJ;
    };

    // Add event handler for the CNPJ field to convert input value to uppercase before validation
    const handleInputChange = (e) => {
      setSupplier({ ...supplier, cnpj: e.target.value.toUpperCase() });
    };

    return (
        <div>
            <form onSubmit={handleFormSubmit}>
                <label htmlFor="nome">Nome:</label>
                <input type="text" id="nome" name="nome" value={supplier.nome} onChange={handleChange} required /><br/>

                <label htmlFor="cnpj">CNPJ/CGC:</label>
                {/* Update the mask to support uppercase letters and numbers, using "**.***.***/****-99" */}
                <InputMask mask="**.***.***/****-99" value={supplier.cnpj} onChange={handleInputChange} /><br/>

                <label htmlFor="nomeContato">Nome Contato:</label>
                <input type="text" id="nomeContato" name="nomeContato" value={supplier.nomeContato} onChange={handleChange} required /><br/>

                <label htmlFor="emailContato">Email Contato:</label>
                <input type="email" id="emailContato" name="emailContato" value={supplier.emailContato} onChange={handleChange} required /><br/>

                <label htmlFor="telefoneContato">Telefone Contato:</label>
                <input type="text" id="telefoneContato" name="telefoneContato" value={supplier.telefoneContato} onChange={handleChange} required /><br/>

                {error && <p>{error}</p>}

                <button type="submit">Create Supplier</button>
            </form>
        </div>
    };
};

export default SupplierForm;
