private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }

    String opt = token.substring(1, 2);
    if (!options.hasShortOption(opt)) {
        return false;
    }

    // Check if the token is just a short option or concatenated options or with value
    // valid short options start with '-' followed by a single option character
    // followed by either nothing, '=' or more characters representing values or concatenations
    if (token.length() == 2) {
        // e.g., -S
        return true;
    } else if (token.charAt(2) == '=' ) {
        // e.g., -S=V
        return true;
    } else if (Character.isDigit(token.charAt(2)) || Character.isLetter(token.charAt(2))) {
        // e.g., -SV, -S1S2
        return true;
    }

    return false;
}
