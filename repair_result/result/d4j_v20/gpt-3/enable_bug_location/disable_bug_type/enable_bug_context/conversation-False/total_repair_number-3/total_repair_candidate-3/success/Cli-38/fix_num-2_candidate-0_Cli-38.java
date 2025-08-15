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

    // Check if the entire optName is a known short option
    if (options.hasShortOption(optName))
    {
        return true;
    }

    // Otherwise, if optName consists of multiple characters,
    // each character might be a short option (concatenated options).
    // So check that every character is a valid short option.
    for (int i = 0; i < optName.length(); i++)
    {
        String s = String.valueOf(optName.charAt(i));
        if (!options.hasShortOption(s))
        {
            return false;
        }
    }
    return true;
}
