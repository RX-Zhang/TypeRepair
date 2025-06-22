private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }
    
    String opt = token.substring(1, 2);

    // Check if the first character after '-' is a known short option
    if (!options.hasShortOption(opt)) {
        return false;
    }

    // If token length is exactly 2, it's a short option e.g. -S
    if (token.length() == 2) {
        return true;
    }
    
    // If the third character is '=', it's a short option with value e.g. -S=V
    if (token.charAt(2) == '=') {
        return true;
    }
    
    // If the token looks like concatenated short options or short option with attached arguments,
    // check if the rest after the first short option is all letters or digits (possible concatenated options) 
    // or if it starts with digit(s) (possible argument), accept it as short option
    // For example: -SV or -S1S2 or -SV1=V2

    // In fact, handleConcatenatedOptions and other handler methods will parse these
    // Here just confirm the first character is a short option, so return true
    return true;
}
