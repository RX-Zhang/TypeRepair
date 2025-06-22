private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            if (!tq.matchChomp(SQ)) { // ensure closing quote consumed to avoid infinite loop/errors
                // no action needed if no closing quote, chompTo returns all
            }
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            if (!tq.matchChomp(DQ)) { // ensure closing quote consumed
                // no action needed if no closing quote, chompTo returns all
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
        if (!tq.isEmpty()) tq.consume();  // only consume if possible, preventing StringIndexOutOfBoundsException
        return null;
    }
}
