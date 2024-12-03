ALTER TABLE SUPPLIER MODIFY COLUMN Cnpj VARCHAR(255) NOT NULL;

CREATE FUNCTION calculateCheckDigit(cnpj VARCHAR(255))
RETURNS INT
BEGIN
    DECLARE checkDigit INT;
    DECLARE c CHAR(1);
    DECLARE asciiValue INT;

    SET checkDigit = 0;
    SET c = SUBSTRING(cnpj, 1, 1);

    WHILE c IS NOT NULL DO
        SET asciiValue = ASCII(c);

        IF asciiValue >= 65 AND asciiValue <= 90 THEN
            SET asciiValue = asciiValue - 48;
        END IF;

        SET checkDigit = (checkDigit + asciiValue) % 11;
        SET c = SUBSTRING(cnpj, LENGTH(cnpj) - LENGTH(cnpj) + 2, 1);
    END WHILE;

    RETURN checkDigit;
END;

CREATE TRIGGER before_insert_supplier
BEFORE INSERT ON SUPPLIER
FOR EACH ROW
BEGIN
    SET NEW.Cnpj = UPPER(NEW.Cnpj);
    SET NEW.Cnpj = REPLACE(NEW.Cnpj, '-', '');
    SET NEW.Cnpj = REPLACE(NEW.Cnpj, '.', '');
END;