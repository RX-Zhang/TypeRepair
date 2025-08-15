private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // use handleShortAndLongOption to validate the token properly
    String t = token.substring(1);
    int pos = t.indexOf("=");

    if (pos == -1)
    {
        return options.hasShortOption(t) || !options.getMatchingOptions(t).isEmpty() || // short or long option without '='
               t.length() > 1; // possibly concatenated short options
    }
    else
    {
        String optName = t.substring(0, pos);
        return options.hasShortOption(optName) || isJavaProperty(optName);
    }
}
