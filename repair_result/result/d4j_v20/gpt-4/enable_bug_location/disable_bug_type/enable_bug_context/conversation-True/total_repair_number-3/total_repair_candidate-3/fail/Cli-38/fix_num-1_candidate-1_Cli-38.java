private boolean isShortOption(String token) {
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() == 1) {
        return false;
    }

    // remove leading "-" and take only the first character as option name
    // For concatenated short options, only the first char is considered an option here
    String optName = token.substring(1, 2);
    return options.hasShortOption(optName);
    // check for several concatenated short options
}
