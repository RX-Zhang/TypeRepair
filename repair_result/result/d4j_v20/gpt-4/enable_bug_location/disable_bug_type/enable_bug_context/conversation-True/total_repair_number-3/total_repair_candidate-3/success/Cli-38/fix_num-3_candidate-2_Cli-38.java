private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    String optName = token.substring(1);

    // check if token contains '='
    int pos = optName.indexOf('=');
    if (pos != -1)
    {
        optName = optName.substring(0, pos);
    }

    if (options.hasShortOption(optName))
    {
        return true;
    }

    // check for concatenated short options like -ab
    for (int i = 0; i < optName.length(); i++)
    {
        if (!options.hasOption(String.valueOf(optName.charAt(i))))
        {
            return false;
        }
    }
    return true;
}
