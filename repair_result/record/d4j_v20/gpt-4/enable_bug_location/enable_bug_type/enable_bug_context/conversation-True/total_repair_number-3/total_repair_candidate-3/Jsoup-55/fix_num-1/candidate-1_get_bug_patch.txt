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
            t.tagPending.attributes().addAttribute("/", "");
            t.error(this);
            t.transition(BeforeAttributeName);
    }
}
