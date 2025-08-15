private boolean isShortOption(String token) throws ParseException
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" and "=value"
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);

    // Check if the option matches a known short option
    if (options.hasShortOption(optName))
    {
        return true;
    }

    // If not, check if it's a concatenation of short options (like -ab)
    if (optName.length() > 1)
    {
        // Verify each character is a valid short option
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

    return false;
}
