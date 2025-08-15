private static StringBuilder escapeRegex(StringBuilder regex, String value, boolean unquote) {
    boolean wasWhite= false;
    for(int i= 0; i<value.length(); ++i) {
        char c= value.charAt(i);
        if(Character.isWhitespace(c)) {
            if(!wasWhite) {
                wasWhite= true;
                regex.append("\\s*+");
            }
            continue;
        }
        wasWhite= false;
        switch(c) {
        case '\'':
            if(unquote) {
                if(++i==value.length()) {
                    return regex;
                }
                c= value.charAt(i);
                // If next char is a whitespace, transform it to \s*+
                if(Character.isWhitespace(c)) {
                    regex.append("\\s*+");
                    continue;
                }
            }
            break;
        case '?':
        case '[':
        case ']':
        case '(':
        case ')':
        case '{':
        case '}':
        case '\\':
        case '|':
        case '*':
        case '+':
        case '^':
        case '$':
        case '.':
            regex.append('\\');
        }
        regex.append(c);
    }
    return regex;
}
