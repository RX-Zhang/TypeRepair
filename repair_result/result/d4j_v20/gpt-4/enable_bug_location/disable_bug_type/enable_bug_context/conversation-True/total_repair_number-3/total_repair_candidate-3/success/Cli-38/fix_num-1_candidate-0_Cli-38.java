private boolean isShortOption(String token) {
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1) {
        return false;
    }

    String optName;
    int pos = token.indexOf("=");
    if (pos == -1) {
        // remove leading "-"
        optName = token.substring(1);
    } else {
        // get string between "-" and "="
        optName = token.substring(1, pos);
    }

    if (options.hasShortOption(optName)) {
        return true;
    }

    // otherwise check if it is a concatenated short option (e.g. -abc)
    if (optName.length() > 1) {
        // verify that every character is a short option
        for (int i = 0; i < optName.length(); i++) {
            String ch = String.valueOf(optName.charAt(i));
            if (!options.hasOption(ch)) {
                return false;
            }
        }
        return true;
    }

    return false;
}
