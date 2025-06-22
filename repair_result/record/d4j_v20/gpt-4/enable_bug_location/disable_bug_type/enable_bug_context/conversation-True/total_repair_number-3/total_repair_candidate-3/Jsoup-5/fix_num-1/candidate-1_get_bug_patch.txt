private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    if (key.length() == 0) {
        if (!tq.isEmpty())
            tq.consume();
        return null;
    }
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            tq.matchChomp(SQ);
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            tq.matchChomp(DQ);
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
