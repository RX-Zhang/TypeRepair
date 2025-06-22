private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) // Changed length check to < 2
    {
        return false;
    }

    // remove leading "-" and "=value"
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);
    
    // Check for several concatenated short options
    if (optName.length() == 1) {
        return options.hasShortOption(optName);
    } else {
        // Check if any of the concatenated options are valid
        for (char c : optName.toCharArray()) {
            if (!options.hasShortOption(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }
}
