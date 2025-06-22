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
        // simple short option
        return true;
    }
    else if (pos == -1 && optName.length() > 1)
    {
        // no '=' and multiple characters: possibly concatenated short options
        // delegate to the handler to check if these are valid concatenated options
        handleConcatenatedOptions(token);
        return true;
    }

    return false;
}
