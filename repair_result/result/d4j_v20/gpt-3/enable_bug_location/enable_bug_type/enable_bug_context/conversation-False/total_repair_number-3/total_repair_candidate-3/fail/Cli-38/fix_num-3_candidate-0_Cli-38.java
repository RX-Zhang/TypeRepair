private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() <= 1) // Changed to <= 1 to include cases like "-"
    {
        return false;
    }

    // remove leading "-" and "=value"
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);
    
    // Check for several concatenated short options
    if (optName.length() > 1) {
        for (char ch : optName.toCharArray()) {
            if (!options.hasShortOption(String.valueOf(ch))) {
                return false;
            }
        }
    }
    
    return options.hasShortOption(optName);
}
