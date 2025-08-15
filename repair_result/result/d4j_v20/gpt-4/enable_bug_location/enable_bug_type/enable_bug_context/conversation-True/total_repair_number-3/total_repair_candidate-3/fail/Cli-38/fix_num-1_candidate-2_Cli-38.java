private boolean isShortOption(String token) throws ParseException
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" and process concatenated short options or options with equal
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);

    if (options.hasShortOption(optName))
    {
        return true;
    }
    else if (optName.length() > 1)
    {
        // handle concatenated short options if all characters are options
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
    return false;
}
