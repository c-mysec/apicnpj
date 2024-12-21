    CREATE TABLE SUPPLIER (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        Nome VARCHAR(255) NOT NULL,
        Cnpj CHAR(14) NOT NULL, -- Modified to support uppercase letters and numbers.
        NomeContato VARCHAR(255),
        EmailContato VARCHAR(255),
        TelefoneContato VARCHAR(20)
    );

    
/* Update the CNPJ/CGC verification routine */
    
-- Extracted function to calculate check digit (DV) using ASCII values.
CREATE FUNCTION CalculateCNPVDigit(cnpj CHAR(14)) RETURNS TINYINT DETERMINISTIC 
BEGIN
    DECLARE i INT DEFAULT 0;
    DECLARE factor INT DEFAULT 5;
    DECLARE dv_result INT := 0;
    
    FOR i = 1 TO 12 DO
        IF SUBSTRING(cnpj, i, 1) REGEXP '[A-Z]' THEN -- Use the uppercase letters and numbers.
            SET dv_result += (ASCII(SUBSTRING(cnpj, i, 1)) - ASCII('0')) * factor;
        ELSEIF SUBSTRING(cnpj, i, 1) REGEXP '[0-9]' THEN
            SET dv_result += CAST(SUBSTRING(cnpj, i, 1) AS UNSIGNED) * factor;
        END IF;
        
        SET factor = CASE WHEN MOD((factor - 1), 2) = 0 AND SUBSTRING(cnpj, i, 1) REGEXP '[A-Z]' THEN 9 ELSE 4 END; -- Increase the factor for next iteration.
    END FOR;
    
    RETURN MOD((dv_result + (factor * (SUBSTRING(cnpj, 12, 1) - ASCII('0')))) / 11), 11);
END//

-- Modified function to validate CNPJ/CGC with updated logic.
CREATE FUNCTION ValidateCNPJ(cnpj CHAR(14)) RETURNS BOOLEAN DETERMINISTIC 
BEGIN
    DECLARE check_digit1 INT;
    DECLARE check_digit2 INT;
    DECLARE calculated_check_digit1 TINYINT;
    DECLARE calculated_check_digit2 TINYINT;
    
    -- Calculate the first and second check digits.
    SET check_digit1 = CalculateCNPVDigit(SUBSTRING(cnpj, 1, 12));
    SET check_digit2 = CalculateCNPVDigit(CONCAT(SUBSTRING(cnpj, 1, 12), CHECK_DIGIT1)); -- Add first calculated check digit.
    
    -- Extract the actual CNPJ/CGC digits for comparison.
    SET check_digit1 = CAST(SUBSTRING(cnpj, 13, 1) AS UNSIGNED);
    SET check_digit2 = CAST(SUBSTRING(cnpj, 14, 1) AS UNSIGNED);
    
    -- Check if the calculated and actual check digits match.
    IF (check_digit1 = 0 AND check_digit2 = 0) OR (calculated_check_digit1 = check_digit1 AND calculated_check_digit2 = check_digit2) THEN
        RETURN TRUE;
    END IF;
    
    -- If there is any error in the CNPJ/CGC input, return FALSE.
    RETURN FALSE;
END//
        