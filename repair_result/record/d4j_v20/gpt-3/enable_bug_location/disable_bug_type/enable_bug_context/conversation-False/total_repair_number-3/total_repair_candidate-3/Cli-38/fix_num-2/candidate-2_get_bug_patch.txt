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

    // For short options, optName should be a single character
    // If optName length > 1, it might be concatenated options like "-ab" or option with argument like "-t1"
    // We only check the first character for short option existence here
    if (optName.length() > 1) {
        optName = optName.substring(0, 1);
    }

    return options.hasShortOption(optName);
}
