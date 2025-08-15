private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" but only consider first character for single short option check here
    // to allow concatenated short options like -ab
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1, 2) : token.substring(1, 2);
    return options.hasShortOption(optName);
}
