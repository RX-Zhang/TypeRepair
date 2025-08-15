private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1, 2) : token.substring(1, 2);
    return options.hasShortOption(optName);
    // check for several concatenated short options
}
