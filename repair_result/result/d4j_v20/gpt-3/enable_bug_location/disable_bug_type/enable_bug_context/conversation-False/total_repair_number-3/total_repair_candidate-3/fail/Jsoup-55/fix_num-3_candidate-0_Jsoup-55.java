void read(Tokeniser t, CharacterReader r) {
    char c = r.consume();
    switch (c) {
        case '>':
            t.emitTagPending();
            t.transition(Data);
            break;
        case eof:
            t.eofError(this);
            t.transition(Data);
            break;
        default:
            t.error(this);
            if (c == '/') {
                t.tagPending.selfClosing = true;
                c = r.consume();
                if (c == '>') {
                    t.emitTagPending();
                    t.transition(Data);
                } else {
                    t.error(this);
                    t.transition(BeforeAttributeName);
                }
            } else {
                t.transition(BeforeAttributeName);
            }
    }
}
