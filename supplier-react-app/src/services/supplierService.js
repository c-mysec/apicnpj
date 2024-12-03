const serverUrl = 'https://sturdy-spork-v54qqr69q3pw95-8080.app.github.dev';

export const getAllSuppliers = async () => {
    const response = await fetch(serverUrl+'/api/suppliers');
    if (!response.ok) {
        throw new Error('Failed to fetch suppliers');
    }
    return await response.json();
};

export const createSupplier = async (supplier) => {
    // Remove non-numeric characters from CNPJ
    const cleanedSupplier = {
        ...supplier,
        cnpj: supplier.cnpj.replace(/[^A-Z0-9]/g, '')
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