void read(Tokeniser t, CharacterReader r) {
    char c = r.consume();
    switch (c) {
        case '>':
            t.emitTagPending();
            t.transition(Data);
            break;
        case '/':
            t.tagPending.selfClosing = true;
            t.transition(SelfClosingStartTag);
            break;
        case eof:
            t.eofError(this);
            t.transition(Data);
            break;
        default:
            t.error(this);
            t.transition(BeforeAttributeName);
    }
}
