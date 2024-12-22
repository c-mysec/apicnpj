    // Add this import statement to include regex pattern for matching uppercase letters and numbers
    import java.util.regex.Pattern;

    // Modify existing CNPJ parser methods:
    
    public static String parseCNPJ(String cnpj) {
        if (cnpj == null || !CodigoUtil.isValidCNPJFormat(cnpj)) {
            return "Invalid CNPJ";
        }
        
        // Convert input to uppercase letters and numbers only using regex pattern
        String cnpjUpperCase = Pattern.compile("[^A-Z0-9]").matcher(cnpj).replaceAll("");
        
        if (cnpjUpperCase.equals(cnpj)) {
            return "Invalid CNPJ"; // If no changes were made, it means the input was invalid
        }
        
        ...
    }
    
    public static boolean isValidCNPJFormat(String cnpj) {
        String regexPattern = "^[A-Z0-9]{14}$|^$"; // Regex pattern for CNPJ validation
        return cnpj.matches(regexPattern);
    }
    
    ... (rest of the class code remains unchanged)