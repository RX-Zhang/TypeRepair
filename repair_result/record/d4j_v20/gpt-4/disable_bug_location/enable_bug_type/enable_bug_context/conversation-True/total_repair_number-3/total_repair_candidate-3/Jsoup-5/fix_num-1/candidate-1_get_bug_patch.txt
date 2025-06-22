private Attribute parseAttribute() {
    tq.consumeWhitespace();
    if (tq.isEmpty())
        return null;
    
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();

    if (!tq.isEmpty() && tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (!tq.isEmpty() && tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            if (!tq.isEmpty()) tq.matchChomp(SQ); // consume closing quote if present
        } else if (!tq.isEmpty() && tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            if (!tq.isEmpty()) tq.matchChomp(DQ); // consume closing quote if present
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
            tq.consume();
        }
        return null;
    }
}
