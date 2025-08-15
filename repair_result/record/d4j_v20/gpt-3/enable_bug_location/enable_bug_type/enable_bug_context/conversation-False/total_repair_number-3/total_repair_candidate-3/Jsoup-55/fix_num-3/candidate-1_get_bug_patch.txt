void read(Tokeniser t, CharacterReader r) {
    char c = r.consume();
    switch (c) {
        case '>':
            t.tagPending.selfClosing = true;
            t.emitTagPending();
            t.transition(Data);
            break;
        case eof:
            t.eofError(this);
            t.transition(Data);
            break;
        default:
            if (Character.isWhitespace(c)) {
                // Handle whitespace appropriately if needed
                t.transition(BeforeAttributeName);
            } else {
                t.error(this);
                t.transition(BeforeAttributeName);
            }
    }
}
