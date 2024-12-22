    import re
    
    def cnpj_validate(cnpj):
        pattern = r"^[A-Z0-9]{14,18}$"
        if not re.match(pattern, cnpj):
            return False
        
        checkers = [56, 62, 71] + range(2, 9)
        digits = list("".join([c for c in cnpj.upper() if c.isalnum()]))
        
        # Calculate the first DV (calculated from checkers[0:5])
        sum_a = 0
        j = len(digits) - 1
        for i, digit in enumerate(checkers):
            sum_a += int(digits[j]) * i + ((i+1)*((i+2)%8))
            j -= 1
            
        dv1 = (sum_a % 11) or 9
        
        # Calculate the second DV (calculated from checkers[0:6])
        sum_b = 0
        for i, digit in enumerate(digits + str(dv1)):
            sum_b += int(digit) * ((i+2)%9) or 9
            
        dv2 = (sum_b % 11) or 9
        
        return (str(dv1), str(dv2)) == digits[-2:]