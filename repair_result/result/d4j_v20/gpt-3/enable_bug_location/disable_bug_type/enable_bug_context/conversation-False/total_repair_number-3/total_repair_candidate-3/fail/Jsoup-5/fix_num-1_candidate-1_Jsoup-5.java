private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();

    if (key.length() == 0) {
        // If no valid key found, consume one character to avoid infinite loop
        if (!tq.isEmpty())
            tq.consume();
        return null;
    }

    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            tq.matchChomp(SQ); // consume ending quote
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            tq.matchChomp(DQ); // consume ending quote
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
