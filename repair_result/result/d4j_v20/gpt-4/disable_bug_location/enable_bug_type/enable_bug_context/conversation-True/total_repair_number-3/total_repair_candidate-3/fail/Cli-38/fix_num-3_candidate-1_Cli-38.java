private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // remove leading "-" and "=value"
    int pos = token.indexOf("=");
    String optName = pos == -1 ? String.valueOf(token.charAt(1)) : token.substring(1, pos);

    // if there are more characters after the first short option character,
    // it might be concatenated short options, so check only the first character
    return options.hasShortOption(optName);
    // check for several concatenated short options
}
