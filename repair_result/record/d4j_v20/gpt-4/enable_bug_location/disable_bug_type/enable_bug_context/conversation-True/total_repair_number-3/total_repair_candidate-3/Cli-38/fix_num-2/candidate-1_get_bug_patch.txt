private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // check if the first character after '-' is a valid short option
    String optName = token.substring(1, 2);
    return options.hasShortOption(optName);
}
