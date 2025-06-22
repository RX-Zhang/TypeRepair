void read(Tokeniser t, CharacterReader r) {
    if (r.matchesLetter()) {
        String name = r.consumeLetterSequence();
        t.tagPending.appendTagName(name.toLowerCase());
        t.dataBuffer.append(name);
        r.advance();
        return;
    }

    if (t.isAppropriateEndTagToken() && !r.isEmpty()) {
        char c = r.consume();
        switch (c) {
            case '\t':
            case '\n':
            case '\f':
            case ' ':
                t.transition(BeforeAttributeName);
                break;
            case '/':
                t.transition(SelfClosingStartTag);
                break;
            case '>':
                t.emitTagPending();
                t.transition(Data);
                break;
            default:
                t.dataBuffer.append(c);
                anythingElse(t, r);
                break;
        }
    } else if (!r.isEmpty()) { // Corrected logical flow to handle non-empty reader
        anythingElse(t, r);
    }
}
