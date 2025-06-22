private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }
    String opt = token.substring(1, 2);
    // must be a known short option AND not a long option starting with --
    return options.hasShortOption(opt) && !token.startsWith("--");
}
