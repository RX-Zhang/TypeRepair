private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
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
    
    // Check if the key is empty after processing
    if (key.length() == 0) {
        // Handle the case where the attribute key is unexpectedly empty
        // This resolves runtime issues when the attribute is malformed
        return null; // or consider throwing an appropriate exception
    }
    
    return Attribute.createFromEncoded(key, value);
}
