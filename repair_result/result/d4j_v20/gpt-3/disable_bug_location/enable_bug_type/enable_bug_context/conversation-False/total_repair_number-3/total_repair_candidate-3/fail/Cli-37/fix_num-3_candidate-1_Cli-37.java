private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }
    // Check if the first character after '-' is a recognized short option
    return options.hasShortOption(token.substring(1, 2));
}
