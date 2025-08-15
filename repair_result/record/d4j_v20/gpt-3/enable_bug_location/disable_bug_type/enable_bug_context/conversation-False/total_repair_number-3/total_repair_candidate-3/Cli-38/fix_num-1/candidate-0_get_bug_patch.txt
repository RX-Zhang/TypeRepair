private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // For short options, only the first character after '-' should be checked
    char firstChar = token.charAt(1);
    String optName = String.valueOf(firstChar);
    return options.hasShortOption(optName);
}
