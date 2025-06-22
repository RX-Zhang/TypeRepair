private boolean isShortOption(String token)
{
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }

    String possibleOpt = token.substring(1, 2);

    if (options.hasShortOption(possibleOpt)) {
        return true;
    }

    return false;
}
