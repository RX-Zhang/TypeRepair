private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    
    // Check if the key is empty after consuming whitespace
    if (key.isEmpty()) {
        tq.consume(); // Consume the invalid character
        return null; // Return null if the key is empty
    }

    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
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
