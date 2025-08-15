private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = "";
    try {
        key = tq.consumeAttributeKey();
    } catch (StringIndexOutOfBoundsException e) {
        // Defensive: if consumeAttributeKey fails, return null to avoid crash
        return null;
    }
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            if (!tq.isEmpty()) {
                value = tq.chompTo(SQ);
                // Consume the matching quote if present to avoid infinite loops or errors
                if (tq.matchChomp(SQ) == false && !tq.isEmpty()) {
                    // Defensive: try to consume next character to progress after missing closing quote
                    tq.consume();
                }
            }
        } else if (tq.matchChomp(DQ)) {
            if (!tq.isEmpty()) {
                value = tq.chompTo(DQ);
                if (tq.matchChomp(DQ) == false && !tq.isEmpty()) {
                    tq.consume();
                }
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
        if (!tq.isEmpty()) {
            // Defensive: only consume if not empty, to avoid StringIndexOutOfBoundsException
            tq.consume();
        }
        return null;
    }
}
