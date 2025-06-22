private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }

    String opt = token.substring(1, 2);

    // hasShortOption expects a single option character, so check just the first character after '-'
    return options.hasShortOption(opt);
}
