private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            if (tq.isEmpty()) { // prevent out of bounds if no closing SQ found
                value = "";
            }
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            if (tq.isEmpty()) { // prevent out of bounds if no closing DQ found
                value = "";
            }
        } else {
            StringBuilder valueAccum = new StringBuilder();
            // no ' or " to look for, so scan to end tag or space (or end of stream)
            while (!tq.isEmpty() && !tq.matchesAny("<", "/>", ">") && !tq.matchesWhitespace()) {
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
            tq.consume();
        }
        return null;
    }
}
