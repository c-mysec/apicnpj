UPDATE supplier 
SET Cnpj = UPPER(REGEXP_REPLACE(Cnpj, '[^A-Z0-9]', '')) 
WHERE Cnpj IS NOT NULL;