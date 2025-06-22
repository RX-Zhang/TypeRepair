private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // if the token is like -t1, check only the first char after '-'
    String optName;
    int pos = token.indexOf("=");
    if (pos != -1) {
        optName = token.substring(1, pos);
    } else {
        // Only the first character after '-' is the short option
        optName = String.valueOf(token.charAt(1));
    }

    return options.hasShortOption(optName);
    // check for several concatenated short options
}
