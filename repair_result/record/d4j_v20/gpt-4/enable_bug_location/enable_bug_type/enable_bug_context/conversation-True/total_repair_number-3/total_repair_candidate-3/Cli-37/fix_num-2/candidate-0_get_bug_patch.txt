private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    String t = token.substring(1);

    if (t.length() == 0) {
        return false;
    }

    // check first character as short option
    if (!options.hasShortOption(t.substring(0, 1))) {
        return false;
    }

    // if length > 1, check if it is concatenated short options or a value
    if (t.length() == 1) {
        return true;
    }

    // if there is an '=' sign, it is a short option with value
    if (t.indexOf('=') != -1) {
        return true;
    }

    // if options has short option with full string, consider it short option
    if (options.hasShortOption(t)) {
        return true;
    }

    // else check if it looks like concatenated short options
    for (int i = 0; i < t.length(); i++) {
        String ch = String.valueOf(t.charAt(i));
        if (!options.hasOption(ch)) {
            return false;
        }
    }

    return true;
}
