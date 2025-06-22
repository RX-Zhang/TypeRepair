private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) // Changed from length == 1 to length < 2
    {
        return false;
    }

    // remove leading "-" and "=value"
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);

    // Check if the option name is a short option
    if (options.hasShortOption(optName)) {
        return true; // Return true if it's a valid short option
    }

    // Check for concatenated short options
    for (int i = 1; i < token.length(); i++) {
        String ch = String.valueOf(token.charAt(i));
        if (!options.hasShortOption(ch)) {
            return false; // If any concatenated option is invalid, return false
        }
    }

    return true; // All checks passed, it's a valid short option
}
