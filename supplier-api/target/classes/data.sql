UPDATE supplier 
SET Cnpj = UPPER(Cnpj),
    NomeContato = 'Contact A',
    EmailContato = 'contactA@example.com',
    TelefoneContato = '123456789'
WHERE Nome = 'Supplier A';

UPDATE supplier 
SET Cnpj = UPPER(Cnpj),
    NomeContato = 'Contact B',
    EmailContato = 'contactB@example.com',
    TelefoneContato = '987654321'
WHERE Nome = 'Supplier B';

UPDATE supplier 
SET Cnpj = UPPER(Cnpj),
    NomeContato = 'Contact C',
    EmailContato = 'contactC@example.com',
    TelefoneContato = '456789123'
WHERE Nome = 'Supplier C';