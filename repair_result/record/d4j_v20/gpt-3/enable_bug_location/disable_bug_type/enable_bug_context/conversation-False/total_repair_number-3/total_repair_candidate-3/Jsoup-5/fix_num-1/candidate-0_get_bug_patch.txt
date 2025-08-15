private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            if (tq.isEmpty()) {
                // missing closing quote, so break out safely
                return null;
            }
            tq.matchChomp(SQ); // consume closing quote
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            if (tq.isEmpty()) {
                // missing closing quote, so break out safely
                return null;
            }
            tq.matchChomp(DQ); // consume closing quote
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
        if (!tq.isEmpty()) tq.consume();

        return null;
    }
}
