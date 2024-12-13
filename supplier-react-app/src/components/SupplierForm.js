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
        setSupplier({ ...supplier, [name]: value.toUpperCase() });
    };

    const validateCNPJ = (cnpj) => {
        const cleanedCNPJ = cnpj.replace(/\W/g, '');
        if (cleanedCNPJ.length !== 14) {
            return false;
        }
        let length = cleanedCNPJ.length - 2;
        let numbers = cleanedCNPJ.substring(0, length);
        let digits = cleanedCNPJ.substring(length);
        let sum = 0;
        let pos = length - 7;

        for (let i = length; i >= 1; i--) {
            sum += (numbers.charCodeAt(length - i) - 48) * pos--;
            if (pos < 2) pos = 9;
        }

        let result = sum % 11 < 2 ? 0 : 11 - sum % 11;
        if (result !== parseInt(digits.charAt(0))) return false;

        length = length + 1;
        numbers = cleanedCNPJ.substring(0, length);
        sum = 0;
        pos = length - 7;

        for (let i = length; i >= 1; i--) {
            sum += (numbers.charCodeAt(length - i) - 48) * pos--;
            if (pos < 2) pos = 9;
        }

        result = sum % 11 < 2 ? 0 : 11 - sum % 11;
        if (result !== parseInt(digits.charAt(1))) return false;
        return true;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');

        if (!supplier.nome || !supplier.cnpj || !supplier.nomeContato || !supplier.emailContato || !supplier.telefoneContato) {
            setError('All fields are required');
            return;
        }

        if (!validateCNPJ(supplier.cnpj)) {
            setError('Invalid CNPJ');
            return;
        }

        try {
            await createSupplier(supplier);
            setSupplier({
                nome: '',
                cnpj: '',
                nomeContato: '',
                emailContato: '',
                telefoneContato: ''
            });
        } catch (err) {
            setError('Failed to create supplier');
        }
    };

    return (
        <div>
            <h2>Create Supplier</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <input type="text" name="nome" placeholder="Nome" value={supplier.nome} onChange={handleChange} required />
                <InputMask
                    mask="**.***.***/****-99"
                    value={supplier.cnpj}
                    onChange={handleChange}
                >
                    {() => <input type="text" name="cnpj" placeholder="CNPJ" required />}
                </InputMask>
                <input type="text" name="nomeContato" placeholder="Nome Contato" value={supplier.nomeContato} onChange={handleChange} required />
                <input type="email" name="emailContato" placeholder="Email Contato" value={supplier.emailContato} onChange={handleChange} required />
                <input type="text" name="telefoneContato" placeholder="Telefone Contato" value={supplier.telefoneContato} onChange={handleChange} required />
                <button type="submit">Create Supplier</button>
            </form>
        </div>
    );
};

export default SupplierForm;