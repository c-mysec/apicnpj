```javascript
const serverUrl = 'https://sturdy-spork-v54qqr69q3pw95-8080.app.github.dev';

export const getAllSuppliers = async () => {
    const response = await fetch(serverUrl+'/api/suppliers');
    if (!response.ok) {
        throw new Error('Failed to fetch suppliers');
    }
    return await response.json();
};

export const createSupplier = async (supplier) => {
    const cleanedSupplier = {
        ...supplier,
        cnpj: supplier.cnpj.replace(/[\W_]/g, '').toUpperCase()
    };

    const response = await fetch(`${serverUrl}/api/suppliers`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(cleanedSupplier),
    });

    if (!response.ok) {
        throw new Error('Failed to create supplier');
    }

    return await response.json();
};

export const checkDigitCnpj = (cnpj) => {
    const cleanedCnpj = cnpj.replace(/[\W_]/g, '').toUpperCase();
    const digits = cleanedCnpj.split('').map((char) => {
        const asciiCode = char.charCodeAt(0) - 48;
        return isNaN(asciiCode) ? 0 : asciiCode;
    });

    let sum = 0;
    let factor = 5;

    for (let i = 0; i < 12; i++) {
        sum += digits[i] * factor;
        factor = factor === 2 ? 9 : factor - 1;
    }

    let remainder = sum % 11;
    const firstDigit = remainder < 2 ? 0 : 11 - remainder;

    sum = 0;
    factor = 6;

    for (let i = 0; i < 13; i++) {
        sum += digits[i] * factor;
        factor = factor === 2 ? 9 : factor - 1;
    }

    remainder = sum % 11;
    const secondDigit = remainder < 2 ? 0 : 11 - remainder;

    return cleanedCnpj.slice(12) === `${firstDigit}${secondDigit}`;
};
```