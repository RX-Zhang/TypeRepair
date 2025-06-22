void read(Tokeniser t, CharacterReader r) {
    char c = r.consume();
    switch (c) {
        case '>':
            t.emitTagPending();
            t.transition(Data);
            break;
        case eof:
            t.eofError(this);
            t.emitTagPending();
            t.transition(Data);
            break;
        case '/':
            if (r.matches(">")) {
                t.tagPending.selfClosing = true;
                r.consume(); // consume '>'
                t.emitTagPending();
                t.transition(Data);
            } else {
                t.error(this);
                t.transition(BeforeAttributeName);
            }
            break;
        default:
            t.error(this);
            t.transition(BeforeAttributeName);
    }
}
