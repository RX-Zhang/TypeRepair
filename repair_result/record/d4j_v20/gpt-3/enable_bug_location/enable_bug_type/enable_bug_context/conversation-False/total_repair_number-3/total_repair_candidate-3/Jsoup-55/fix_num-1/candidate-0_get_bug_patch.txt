void read(Tokeniser t, CharacterReader r) {
    char c = r.consume();
    switch (c) {
        case '>':
            t.tagPending.selfClosing = true;
            t.emitTagPending();
            t.transition(Data);
            break;
        case '\u0000': // Corrected to handle the end of file character properly
            t.eofError(this);
            t.transition(Data);
            break;
        default:
            t.error(this);
            t.transition(BeforeAttributeName);
    }
}
