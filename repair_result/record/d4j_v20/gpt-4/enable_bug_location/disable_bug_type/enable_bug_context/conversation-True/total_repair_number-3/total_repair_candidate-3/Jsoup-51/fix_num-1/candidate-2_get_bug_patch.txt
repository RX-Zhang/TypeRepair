boolean matchesLetter() {
    if (isEmpty())
        return false;
    String tagName = consumeTagName();
    if (tagName.isEmpty())
        return false;
    char c = tagName.charAt(0);
    return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c > 127); // support non-ASCII chars
}
