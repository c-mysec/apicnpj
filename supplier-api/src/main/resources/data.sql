    // Define a function to convert input string to uppercase letters only
    def format_cnpj(input: str) -> str:
        return ''.join([char for char in input if (ord('A') <= ord(char.upper()) <= ord('Z')) or (ord('0') <= ord(char.upper()) <= ord('9'))])
    
    // Define a function to calculate the check digits based on CNPJ/CGC with uppercase letters and numbers support
    def get_cnpj_checkdigits(input: str) -> tuple[str, str]:
        input = format_cnpj(input.upper())
        
        if len(input) != 14 or not re.match('^([A-Z0-9]*)$', input):
            raise ValueError("Invalid CNPJ/CGC")
            
        # Calculate DVs with ASCII code adjustments
        dv_part = ''.join(str((ord(c) - 48)) for c in input[12:]) + input[:-2]
        
        # Rest of the algorithm remains unchanged.
        # ... (Algorithm to calculate DVs with ASCII code adjustments goes here.)
        
        return dv_part, '...'
    
    // Update parsers and formatters as needed
    def parse_cnpj(input: str) -> bool:
        try:
            # Validate input length (14 characters), then use get_cnpj_checkdigits function.
            return True  # placeholder, needs actual implementation to verify CNPJ/CGC
        except ValueError as e:
            print(e)
            return False
        
    def format_cnpj(input: str) -> str:
        return ''.join([char for char in input if (ord('A') <= ord(char.upper()) <= ord('Z')) or (ord('0') <= ord(char.upper()) <= ord('9'))])