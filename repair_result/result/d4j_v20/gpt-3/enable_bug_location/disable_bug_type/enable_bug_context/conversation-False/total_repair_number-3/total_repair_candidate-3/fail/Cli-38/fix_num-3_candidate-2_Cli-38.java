private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-"
    String optName;
    int pos = token.indexOf("=");

    // in case of something like "-t1" the short option is only the first character after "-"
    if (pos == -1) {
        optName = token.substring(1, 2);
    } else {
        optName = token.substring(1, pos);
    }

    return options.hasShortOption(optName);
}
