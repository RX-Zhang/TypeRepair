private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            tq.matchChomp(SQ); // consume closing '
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            tq.matchChomp(DQ); // consume closing "
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
        // If key is empty, consume one character so parser advances and avoid infinite loops
        tq.consume();
        return null;
    }
}
