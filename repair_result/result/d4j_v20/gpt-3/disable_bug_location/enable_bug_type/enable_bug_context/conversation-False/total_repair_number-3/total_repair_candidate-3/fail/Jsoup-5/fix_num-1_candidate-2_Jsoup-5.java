private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            // Consume the trailing single quote if present
            if (!tq.isEmpty() && tq.matchChomp(SQ)) {
                // consumed closing SQ
            }
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            // Consume the trailing double quote if present
            if (!tq.isEmpty() && tq.matchChomp(DQ)) {
                // consumed closing DQ
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
        // Defensive: only consume if not empty to avoid potential exceptions
        if (!tq.isEmpty()) {
            tq.consume();
        }
        return null;
    }
}
