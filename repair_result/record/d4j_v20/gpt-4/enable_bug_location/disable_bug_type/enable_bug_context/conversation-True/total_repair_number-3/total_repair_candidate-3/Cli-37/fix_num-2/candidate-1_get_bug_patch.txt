private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }
    
    String shortOpt = token.substring(1, 2);

    if (!options.hasShortOption(shortOpt)) {
        return false;
    }

    // If the token length is exactly 2, it's a short option like -S
    if (token.length() == 2) {
        return true;
    }

    // If there is an '=' after the short option, e.g., -S=V
    if (token.charAt(2) == '=') {
        return true;
    }

    // Otherwise, ensure the rest of the token is either multiple short options or value
    // but we should confirm options.hasOption for each char (concatenated options)
    // For the purpose of this check, only first char matters here
    return true;
}
