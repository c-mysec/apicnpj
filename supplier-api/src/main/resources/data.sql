    def validate_cnpj(nbr):
        nbr = ''.join(filter(str.isalnum, nbr.upper()))
        if len(nbr) != 14:
            return False
        
        weights1 = [5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2]
        weights2 = [6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2]
        
        dv1 = calculate_dv(nbr[:-2], weights1)
        dv2 = calculate_dv((dv1 + nbr[-2:])[:-1], weights2)
        
        return str(dv1) == nbr[-2:] and str(dv2) == nbr[-1]
    
    def calculate_dv(nbr, weights):
        result = sum([int(i)*j for i, j in zip(reversed(str(nbr)), weights)])
        modulus = 11 if len(nbr) == 12 else 10
        
        dv = 11 - (result % modulus)
        return str(dv) if dv <=9 else 'A'
    
    def cnpj_format(cnpj):
        nbr = filter(str.isdigit, cnpj.upper())
        
        formatted_cnpj = '{}.{}.{}/{}-{}'.format(nbr[:2], nbr[2:5], nbr[5:8], nbr[8:12], nbr[-2:])
        
        return formatted_cnpj