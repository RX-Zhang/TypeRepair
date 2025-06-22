private Attribute parseAttribute() {
    tq.consumeWhitespace();
    String key = tq.consumeAttributeKey();
    String value = "";
    tq.consumeWhitespace();
    if (tq.matchChomp("=")) {
        tq.consumeWhitespace();

        if (tq.matchChomp(SQ)) {
            value = tq.chompTo(SQ);
            if (tq.matchChomp(SQ) == false) {
                // Prevent StringIndexOutOfBoundsException by checking if the closing SQ exists
                // If not found, fallback to consuming remaining input or handle as empty value
                value += tq.remainder();
                tq.consumeToEnd();
            }
        } else if (tq.matchChomp(DQ)) {
            value = tq.chompTo(DQ);
            if (tq.matchChomp(DQ) == false) {
                // Similarly, prevent exception if closing DQ is missing
                value += tq.remainder();
                tq.consumeToEnd();
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
            tq.consume();
        }
        return null;
    }
}
