private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" and if token has "=" remove from there
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);

    // For concatenated short options like -ab, only check first character to see if it's a short option
    if (optName.length() > 1)
    {
        return options.hasShortOption(String.valueOf(optName.charAt(0)));
    }

    return options.hasShortOption(optName);
}
