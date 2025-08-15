private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            if (!tq.isEmpty() && tq.matches(SQ)) {
                tq.consume(); // consume closing quote if present to avoid index errors
            }
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            if (!tq.isEmpty() && tq.matches(DQ)) {
                tq.consume(); // consume closing quote if present to avoid index errors
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
    if (key.length() != 0)
        return Attribute.createFromEncoded(key, value);
    else {
        // Defensive: only consume if not empty, to avoid index issues
        if (!tq.isEmpty()) {
            tq.consume();
        }
        return null;
    }
}
