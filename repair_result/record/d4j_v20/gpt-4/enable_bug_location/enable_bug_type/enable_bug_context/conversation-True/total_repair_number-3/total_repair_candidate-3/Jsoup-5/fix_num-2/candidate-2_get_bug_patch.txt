private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    if (key.length() == 0) {
        // No valid key found, consume one character to avoid infinite loop and return null
        if (!tq.isEmpty()) {
            tq.consume();
        }
        return null;
    }
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            if (!tq.isEmpty()) {
                tq.consume(); // consume closing quote
            }
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            if (!tq.isEmpty()) {
                tq.consume(); // consume closing quote
            }
        } else {
            StringBuilder valueAccum = new StringBuilder();
            // no ' or " to look for, so scan to end tag or space (or end of stream)
            while (!tq.matchesAny("<", "/>", ">") && !tq.matchesWhitespace() && !tq.isEmpty()) {
                valueAccum.append(tq.consume());
            }
            value = valueAccum.toString();
        }
        tq.consumeWhitespace();
    }
    return Attribute.createFromEncoded(key, value);
}
