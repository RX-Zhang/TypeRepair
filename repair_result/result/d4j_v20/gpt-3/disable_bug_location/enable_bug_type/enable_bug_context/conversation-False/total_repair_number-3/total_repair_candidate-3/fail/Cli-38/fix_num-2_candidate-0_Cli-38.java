private boolean isShortOption(String token)
{
    // short options (-S, -S=V)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // If token is like "-ab" (concatenated short options), check only first char "a"
    // If token is like "-t1" treat "t1" as long option (or normal option).
    int pos = token.indexOf("=");
    String optName = null;
    if (pos != -1) {
        optName = token.substring(1, pos);
    } else {
        optName = token.substring(1);
    }

    // If optName length == 1, check for short option directly
    if (optName.length() == 1) {
        return options.hasShortOption(optName);
    }

    // If optName length > 1, check only first char for short option (concatenated short options)
    // or check entire optName as option - here we treat as NOT short option for correct parsing 
    // (e.g. support options like "-t1" as a full option)
    return false;
}
