private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-"
    String optName = token.substring(1);

    // Check if the option is recognized as a short option
    if (options.hasShortOption(optName))
    {
        return true;
    }

    // Check if the initial character of optName is a short option (for concatenated short options)
    String firstChar = String.valueOf(optName.charAt(0));
    if (options.hasShortOption(firstChar))
    {
        return true;
    }

    return false;
}
