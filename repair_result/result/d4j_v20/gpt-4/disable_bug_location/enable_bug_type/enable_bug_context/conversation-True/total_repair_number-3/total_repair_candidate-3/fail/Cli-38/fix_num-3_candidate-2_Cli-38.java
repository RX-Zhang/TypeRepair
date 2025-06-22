private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" and "=value"
    int pos = token.indexOf("=");
    String optName = pos == -1 ? String.valueOf(token.charAt(1)) : token.substring(1, pos);

    if (options.hasShortOption(optName))
    {
        return true;
    }

    // handle concatenated short options (e.g. -ab)
    if (token.length() > 2)
    {
        // check if all characters after '-' are valid short options
        for (int i = 1; i < token.length(); i++)
        {
            String ch = String.valueOf(token.charAt(i));
            if (!options.hasOption(ch))
            {
                return false;
            }
        }
        return true;
    }

    return false;
}
