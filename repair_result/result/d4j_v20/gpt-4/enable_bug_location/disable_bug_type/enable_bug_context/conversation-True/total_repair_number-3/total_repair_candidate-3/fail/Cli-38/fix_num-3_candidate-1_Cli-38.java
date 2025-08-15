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

    if (optName.length() == 1)
    {
        return options.hasShortOption(optName);
    }
    else if (optName.length() > 1)
    {
        // check if this is a concatenated short option or a java property
        if (options.hasShortOption(String.valueOf(optName.charAt(0))))
        {
            return true;
        }
    }

    return false;
}
