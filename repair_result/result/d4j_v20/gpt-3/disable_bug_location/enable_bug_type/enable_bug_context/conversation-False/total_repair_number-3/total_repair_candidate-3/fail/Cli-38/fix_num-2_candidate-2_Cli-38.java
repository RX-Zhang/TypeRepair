private boolean isShortOption(String token)
{
    // short options are single character options prefixed with '-'
    if (!token.startsWith("-") || token.length() == 1)
    {
        return false;
    }

    // Remove leading "-" and ignore any value after "="
    int pos = token.indexOf("=");
    String optName = pos == -1 ? token.substring(1) : token.substring(1, pos);

    // If optName length is 1, check if that short option exists
    if (optName.length() == 1) {
        return options.hasShortOption(optName);
    }

    // If optName has length > 1, it might be concatenated short options
    // We consider it not a short option token but a concatenation for parsing,
    // so return false here to let handleConcatenatedOptions process it
    return false;
}
