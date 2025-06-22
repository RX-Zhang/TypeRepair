void read(Tokeniser t, CharacterReader r) {
    int c = r.consume();
    switch (c) {
        case '>':
            t.tagPending.selfClosing = true;
            t.emitTagPending();
            t.transition(Data);
            break;
        case CharacterReader.EOF:
            t.eofError(this);
            t.transition(Data);
            break;
        default:
            t.error(this);
            t.transition(BeforeAttributeName);
    }
}
