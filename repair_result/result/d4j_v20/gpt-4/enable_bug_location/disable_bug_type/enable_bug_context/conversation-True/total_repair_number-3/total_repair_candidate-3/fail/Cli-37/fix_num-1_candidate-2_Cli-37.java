private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }
    String t = token.substring(1);

    // Check if the first character after the dash is a recognized short option
    if (options.hasShortOption(t.substring(0, 1))) {
        return true;
    }

    return false;
}
