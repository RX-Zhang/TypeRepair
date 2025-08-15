private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" 
    String optName = token.substring(1);

    if (options.hasShortOption(optName))
    {
        return true;
    }

    // if the whole optName is not a short option, check for concatenated short options
    for (int i = 0; i < optName.length(); i++)
    {
        String ch = String.valueOf(optName.charAt(i));
        if (!options.hasOption(ch))
        {
            return false;
        }
    }

    return true;
}
