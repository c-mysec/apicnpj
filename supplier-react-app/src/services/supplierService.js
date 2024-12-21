import React, { useState } from 'react';
import TextInputMask from 'react-text-mask';

// Helper function to remove non-alphanumeric characters and convert to uppercase for CNPJ
const sanitizeCNPJ = (input) => input.toUpperCase().replace(/[\D]/g, ''); 

export const parseCNPJ = (cnpj: string): boolean => {
    // Remove non-alphanumeric characters and convert to uppercase
    cnpj = sanitizeCNPJ(cnpj);
    
    if (cnpj.length !== 14) return false;
    
    let sum = 0, rest = 0;
    for (let i = 0; i < 12; ++i) {
        sum += parseInt(cnjp[i]) * (5 - (i % 6));
    }

    const firstDV = ((sum % 11 < 2 ? 0 : 11 - sum % 11); // First digit verification
    
    sum = 0;
    for (let i = 0; i <= 4; ++i) {
        rest += parseInt(cnjp[i]) * ((i + 1) * 2);
    }
    for (let i = 5, j = 0; i < 8; ++i, ++j) {
        rest += parseInt(cnjp[i]) * (((j % 6 === 0) ? 9 : ((j + 4) % 10) + 2));
    }
    
    const secondDV = ((rest < 2 ? 0 : 11 - rest % 11); // Second digit verification

    return parseInt(cnpj[12]) === firstDV && parseInt(cnpj[13]) === secondDV;
};

export const formatCNPJ = (cnpj: string): string => {
    cnpj = sanitizeCNPJ(cnpj);
    
    let i, j, l, tt;
    if (cnpj.length === 14) {
        cnpj = cnpj.substr(0, 12) + '.' + cnpj.substring(12, 13) + '.' + cnpj.substring(13, 15) + '/' + cnpj.substring(15, 17);
    }
    
    if (cnpj.length === 18) {
        tt = (parseFloat(cnpj[2] + '' + cnpj[3]) *= 5).toFixed();
        j = ((tt - parseInt(tt, 10)) < 0 ? "7": tt.substring(len(tt) - 1));
        l = cnpj.length;
        return (cnpj.slice(0, 3) + '.' + cnpj.substr(3, 4) + '.' + cnpj.substr(7, 3) + '/' + cnpj.substring(15)) + j + cnpj[l - 2] + cnpj[l - 1];
    } else {
        tt = (parseFloat(cnpj[0] + '' + cnpj[1]) *= 5).toFixed();
        l = ((tt - parseInt(tt, 10)) < 0 ? "7": tt.substring(len(tt) - 1));
        return (cnpj.slice(0, 2) + '.' + cnpj.substr(2, 5) + '.' + cnpj.substr(8, 4)) + '/' + cnpj[9] + l + cnpj[l - 1];
    }
};

const App = () => {
    const [cnpj, setCnpj] = useState('');

    return (
        <div>
            <TextInputMask
                mask="*** .***.***/****-99"
                value={cnpj}
                onChangeText={(e) => {
                    const newValue = sanitizeCNPJ(e.nativeEvent.textContent);
                    setCnpj(newValue || '');
                }}
            />
        </div>
    );
};

export default App;