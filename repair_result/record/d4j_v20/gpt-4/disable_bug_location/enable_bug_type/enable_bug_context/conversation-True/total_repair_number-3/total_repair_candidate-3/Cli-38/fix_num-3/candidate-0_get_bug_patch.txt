private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-"
    String optName = token.substring(1);

    // if token contains '=', only consider part before '=' as option name
    int pos = optName.indexOf("=");
    if (pos != -1) {
        optName = optName.substring(0, pos);
    }

    // If the option is a single character or multiple concatenated short options
    if (optName.length() > 1 && options.hasOption(String.valueOf(optName.charAt(0)))) {
        return true;
    }

    return options.hasShortOption(optName);
}
