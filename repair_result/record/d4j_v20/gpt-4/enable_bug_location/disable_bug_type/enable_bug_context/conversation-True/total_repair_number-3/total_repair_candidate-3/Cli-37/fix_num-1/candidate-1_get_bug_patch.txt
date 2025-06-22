private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }

    String t = token.substring(1);

    int pos = t.indexOf('=');
    String opt = pos == -1 ? t.substring(0, 1) : t.substring(0, pos);

    return options.hasShortOption(opt);
}
