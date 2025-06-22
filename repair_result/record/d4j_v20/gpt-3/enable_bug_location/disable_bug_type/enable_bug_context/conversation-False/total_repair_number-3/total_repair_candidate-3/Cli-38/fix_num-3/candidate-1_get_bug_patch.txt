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

    // If optName length is greater than 1, check if the first character is a short option
    if (optName.length() > 1)
    {
        return options.hasShortOption(String.valueOf(optName.charAt(0)));
    }
    else
    {
        return options.hasShortOption(optName);
    }
}
