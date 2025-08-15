private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-"
    String optName = token.substring(1);

    // if there is an '=', consider only up to '=' for option name
    int pos = optName.indexOf("=");
    if (pos != -1)
    {
        optName = optName.substring(0, pos);
    }

    // Check if options contains the full optName (for concatenated options, try each char)
    if (options.hasShortOption(optName))
    {
        return true;
    }

    // If not found as a whole, try checking each character (for concatenated short options)
    for (int i = 0; i < optName.length(); i++)
    {
        String ch = String.valueOf(optName.charAt(i));
        if (!options.hasShortOption(ch))
        {
            return false;
        }
    }

    return true;
}
