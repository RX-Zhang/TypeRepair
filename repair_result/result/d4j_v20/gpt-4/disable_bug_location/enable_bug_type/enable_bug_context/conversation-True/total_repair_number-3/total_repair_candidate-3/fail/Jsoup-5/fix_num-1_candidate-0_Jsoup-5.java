private Attribute parseAttribute() {
    tq.consumeWhitespace();
    if (tq.isEmpty()) {
        return null;
    }
    String key = tq.consumeAttributeKey();
    if (key.length() == 0) {
        // consume one character to avoid infinite loop on malformed input
        if (!tq.isEmpty()) {
            tq.consume();
        }
        return null;
    }
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (!tq.isEmpty()) {
            if (tq.matchChomp(SQ)) {
                value = tq.chompTo(SQ);
                if (!tq.isEmpty() && tq.matchChomp(SQ)) {
                    // consume closing quote if still there (defensive)
                }
            } else if (tq.matchChomp(DQ)) {
                value = tq.chompTo(DQ);
                if (!tq.isEmpty() && tq.matchChomp(DQ)) {
                    // consume closing quote if still there (defensive)
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
    }
    return Attribute.createFromEncoded(key, value);
}
