    CREATE TABLE SUPPLIER (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(255) NOT NULL,
    Cnpj CHAR(14) NOT NULL, -- Changed from BIGINT to CHAR for supporting uppercase letters and numbers.
    NomeContato VARCHAR(255),
    EmailContato VARCHAR(255),
    TelefoneContato VARCHAR(20)
);

-- Function to validate CNPJ with new format requirements
DELIMITER $$
CREATE FUNCTION ValidateCnpj(cnpj CHAR(14)) RETURNS TINYINT 
BEGIN
    DECLARE checkSum INT;
    
    -- Convert input string to uppercase using ASCII values and subtracting 32 for converting lowercase letters.
    SET cnpj = UPPER(REPLACE(cnpj, LOWER(cnpj), SUBSTRING(UPPER(cnpj), INSTR(UPPER(cnpj), LOWER(SUBSTR(cnpj, 1, LOCATE('-', cnpj))))) - 32));
    
    -- CNPJ validation logic goes here. Modify the algorithm to use ASCII values for non-numeric characters as described above (e.g., A=17).
    RETURN 1; -- Return true if valid, else return 0
END $$
DELIMITER ;