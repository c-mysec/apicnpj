CREATE TABLE SUPPLIER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Cnpj CHAR(14) NOT NULL, -- changed to CHAR(14) to store uppercase letters and numbers
    NomeContato VARCHAR(255),
    EmailContato VARCHAR(255),
    TelefoneContato VARCHAR(20)
);

-- Function for validating CNPJ format with upper case letters
CREATE FUNCTION validateCNPJ (cnpj CHAR(14)) RETURNS BOOLEAN
BEGIN
   DECLARE cnt INT;
    
    SELECT COUNT(*) INTO cnt FROM SUPPLIER WHERE TRIM(Cnpj) = LOWER(TRIM(cnpj));
    
  IF cnt != 1 OR NOT REGEXP_LIKE(TRIM(cnpj), '^[A-Z0-9]{14}$') THEN
    RETURN FALSE;
  ELSE
    CALL convertCnpjToUpperCase(cnpj);
    CALL calculateCheckDigitWithASCIIValues(cnpj); -- assume we have a function that calculates check digits using ASCII values
    RETURN TRUE;
  END IF;
END $$
DELIMITER ;