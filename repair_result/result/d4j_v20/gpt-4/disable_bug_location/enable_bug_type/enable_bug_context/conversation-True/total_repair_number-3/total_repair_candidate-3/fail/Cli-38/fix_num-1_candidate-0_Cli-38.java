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

    if (options.hasShortOption(optName))
    {
        return true;
    }

    // check if the token can be treated as concatenated short options or special cases
    // fallback is false if none matches
    return false;
}
