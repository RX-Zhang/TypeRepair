private boolean isShortOption(String token) throws ParseException
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" and optionally process "=" for value
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);

    if (options.hasShortOption(optName))
    {
        return true;
    }
    else if (optName.length() > 1)
    {
        // if there is no exact short option but possibly concatenated short options
        handleConcatenatedOptions(token);
        return true;
    }
    return false;
}
