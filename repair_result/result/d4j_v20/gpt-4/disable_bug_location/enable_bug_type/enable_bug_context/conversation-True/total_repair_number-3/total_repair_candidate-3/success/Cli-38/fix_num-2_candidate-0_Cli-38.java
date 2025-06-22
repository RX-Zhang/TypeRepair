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

    // check if the entire optName is a known short option
    if (options.hasShortOption(optName)) {
        return true;
    }

    // If not, it might be concatenated short options (e.g. "ab" in "-ab")
    // We check if each char is a known short option
    for (int i = 0; i < optName.length(); i++) {
        String ch = String.valueOf(optName.charAt(i));
        if (!options.hasOption(ch)) {
            return false;
        }
    }
    return true;
}
