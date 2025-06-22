private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    String optName;
    int pos = token.indexOf("=");

    if (pos == -1)
    {
        optName = token.substring(1);
    }
    else
    {
        optName = token.substring(1, pos);
    }

    if (optName.length() == 1)
    {
        return options.hasShortOption(optName);
    }
    else if (options.hasShortOption(optName))
    {
        return true;
    }
    else
    {
        // check if the first character is a known short option and the rest 
        // is argument or concatenated options like -SV1 or -S1S2
        String firstChar = optName.substring(0, 1);
        return options.hasShortOption(firstChar);
    }
}
