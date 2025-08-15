private void printAndQuote(final Object object, final CharSequence value, final int offset, final int len,
        final Appendable out, final boolean newRecord) throws IOException {
    boolean quote = false;
    int start = offset;
    int pos = offset;
    final int end = offset + len;

    final char delimChar = getDelimiter();
    final Character quoteCharObj = getQuoteCharacter();
    final char quoteChar = quoteCharObj != null ? quoteCharObj.charValue() : 0;

    QuoteMode quoteModePolicy = getQuoteMode();
    if (quoteModePolicy == null) {
        quoteModePolicy = QuoteMode.MINIMAL;
    }

    Character escapeChar = getEscapeCharacter();

    switch (quoteModePolicy) {
    case ALL:
        quote = true;
        break;
    case NON_NUMERIC:
        quote = !(object instanceof Number);
        break;
    case NONE:
        // Use the existing escaping code
        printAndEscape(value, offset, len, out);
        return;
    case MINIMAL:
        if (len <= 0) {
            if (newRecord) {
                quote = true;
            }
        } else {
            char c = value.charAt(pos);

            // RFC4180 (https://tools.ietf.org/html/rfc4180) TEXTDATA =  %x20-21 / %x23-2B / %x2D-7E
            if (newRecord && (c < '0' || (c > '9' && c < 'A') || (c > 'Z' && c < 'a') || c > 'z')) {
                quote = true;
            } else if (c <= COMMENT) {
                // encapsulate if starting with anything less than '#'
                quote = true;
            } else {
                while (pos < end) {
                    c = value.charAt(pos);
                    if (c == LF || c == CR || (quoteCharObj != null && c == quoteChar) || c == delimChar) {
                        quote = true;
                        break;
                    }
                    pos++;
                }

                if (!quote) {
                    pos = end - 1;
                    c = value.charAt(pos);
                    if (c <= SP) {
                        quote = true;
                    }
                }
            }
        }

        if (!quote) {
            out.append(value, start, end);
            return;
        }
        break;
    default:
        throw new IllegalStateException("Unexpected Quote value: " + quoteModePolicy);
    }

    if (!quote) {
        out.append(value, start, end);
        return;
    }

    out.append(quoteChar);

    while (pos < end) {
        final char c = value.charAt(pos);
        if (quoteCharObj != null && c == quoteChar) {
            out.append(value, start, pos);
            if (escapeChar != null) {
                out.append(escapeChar.charValue());
            }
            out.append(quoteChar);
            start = pos + 1;
        }
        pos++;
    }

    out.append(value, start, pos);
    out.append(quoteChar);
}
