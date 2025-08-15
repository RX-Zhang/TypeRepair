private boolean isShortOption(String token)
{
    // short options (-S, -SV, -S=V, -SV1=V2, -S1S2)
    if (!token.startsWith("-") || token.length() < 2) {
        return false;
    }
    
    String opt = token.substring(1, 2);
    if (!options.hasShortOption(opt)) {
        return false;
    }

    // If the token length is exactly 2 (e.g. -t), it's a short option.
    if (token.length() == 2) {
        return true;
    }

    // if the third character is '=', treat as short option (e.g. -S=V)
    if (token.charAt(2) == '=') {
        return true;
    }

    // if the third character is not a letter or digit, it's not a short option group
    char ch = token.charAt(2);
    // If the third character is not a valid option char or is a symbol not allowed in short options,
    // then treat as not a short option here.
    // However, commons-cli allows concatenated options (e.g. -abc), so accept letters or digits.
    if (Character.isLetterOrDigit(ch)) {
        return true;
    }

    return false;
}
