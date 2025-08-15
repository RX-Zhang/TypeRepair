private void printAndQuote(final Object object, final CharSequence value, final int offset, final int len,
        final Appendable out, final boolean newRecord) throws IOException {
    boolean quote = false;
    int start = offset;
    int pos = offset;
    final int end = offset + len;

    final char delimChar = getDelimiter();
    final char quoteChar = getQuoteCharacter().charValue();

    QuoteMode quoteModePolicy = getQuoteMode();
    if (quoteModePolicy == null) {
        quoteModePolicy = QuoteMode.MINIMAL;
    }
    switch (quoteModePolicy) {
    case ALL:
    case ALL_NON_NULL:
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
            // always quote an empty token that is the first
            // on the line, as it may be the only thing on the
            // line. If it were not quoted in that case,
            // an empty line has no tokens.
            if (newRecord) {
                quote = true;
            }
        } else {
            char c = value.charAt(pos);

            // Fix the logical expression here to correctly detect characters needing quotes:
            // Quote if starting char is control char (less than space)
            // or if char is quoteChar, delimiter, LF, CR
            // or if char is commentMarker
            // In particular, we should not quote the Euro sign (unicode 0x20AC),
            // so exclude that from quoting conditions.

            // Better approach: quote if char is control (<= 0x1F) or equals quoteChar, delimiter, LF, CR, or commentMarker
            if (c <= 0x1F || c == quoteChar || c == delimChar || c == '\n' || c == '\r' || (getCommentMarker() != null && c == getCommentMarker())) {
                quote = true;
            } else {
                while (pos < end) {
                    c = value.charAt(pos);
                    if (c == LF || c == CR || c == quoteChar || c == delimChar) {
                        quote = true;
                        break;
                    }
                    pos++;
                }

                if (!quote) {
                    pos = end - 1;
                    c = value.charAt(pos);
                    // Quote if last char is control or whitespace (less than or equal to space)
                    if (c <= ' ') {
                        quote = true;
                    }
                }
            }
        }

        if (!quote) {
            // no encapsulation needed - write out the original value
            out.append(value, start, end);
            return;
        }
        break;
    default:
        throw new IllegalStateException("Unexpected Quote value: " + quoteModePolicy);
    }

    if (!quote) {
        // no encapsulation needed - write out the original value
        out.append(value, start, end);
        return;
    }

    // we hit something that needed encapsulation
    out.append(quoteChar);

    // Pick up where we left off: pos should be positioned on the first character that caused
    // the need for encapsulation.
    while (pos < end) {
        final char c = value.charAt(pos);
        if (c == quoteChar) {
            // write out the chunk up until this point

            // add 1 to the length to write out the encapsulator also
            out.append(value, start, pos + 1);
            // put the next starting position on the encapsulator so we will
            // write it out again with the next string (effectively doubling it)
            start = pos;
        }
        pos++;
    }

    // write the last segment
    out.append(value, start, pos);
    out.append(quoteChar);
}
