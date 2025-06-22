private boolean isShortOption(String token)
{
    // short options (-S)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }

    // extract the first character after '-'
    String opt = token.substring(1, 2);

    // check if the first character is a known short option
    if (!options.hasShortOption(opt)) {
        return false;
    }

    // if token has length 2, it's a simple short option like "-t"
    if (token.length() == 2) {
        return true;
    }

    // if the third character is '=', then it's a short option with a value like "-t=value"
    if (token.charAt(2) == '=') {
        return true;
    }

    // Otherwise, if the token is longer than 2 and the third character is not '=', 
    // it could be concatenated options or argument (e.g., "-abc" or "-t1")
    // but only consider it a short option if the rest are arguments rather than other options
    // So here we treat tokens like "-t1" as a short option with an argument, not as multiple short options
    return true;
}
