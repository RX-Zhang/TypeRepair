private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" and "=value"
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);

    // Only the first character is considered a short option.
    // If optName has length > 1, it's concatenated options or an option with attached value.
    // If options.hasShortOption(optName) is false, check first char only.
    if (options.hasShortOption(optName))
    {
        return true;
    }
    else if (optName.length() > 1)
    {
        // Check if the first char is a known short option (concatenated options scenario)
        String firstChar = optName.substring(0, 1);
        return options.hasShortOption(firstChar);
    }
    return false;
}
