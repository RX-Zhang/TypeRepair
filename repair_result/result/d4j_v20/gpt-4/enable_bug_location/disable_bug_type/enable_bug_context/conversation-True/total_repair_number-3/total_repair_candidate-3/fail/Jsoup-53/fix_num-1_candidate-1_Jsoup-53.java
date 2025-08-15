public String chompBalanced(char open, char close) {
    int start = -1;
    int end = -1;
    int depth = 0;
    char last = 0;

    while (!isEmpty()) {
        char c = peek();
        if (last != ESC) {
            if (c == open) {
                depth++;
                if (start == -1)
                    start = pos + 1;
                advance();
                last = c;
                continue;
            } else if (c == close) {
                depth--;
                advance();
                if (depth == 0)
                    break;
                last = c;
                continue;
            }
        }
        advance();
        if (depth > 0)
            end = pos;
        last = c;
    }
    return (start >= 0 && end >= start) ? queue.substring(start, end) : "";
}
