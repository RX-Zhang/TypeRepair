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

    // For concatenated short options, check each character individually
    if (optName.length() > 1)
    {
        for (int i = 0; i < optName.length(); i++)
        {
            if (!options.hasOption(String.valueOf(optName.charAt(i))))
            {
                return false;
            }
        }
        return true;
    }
    else
    {
        return options.hasOption(optName);
    }
}
