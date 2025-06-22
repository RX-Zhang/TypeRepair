private void printAndQuote(final Object object, final CharSequence value, final int offset, final int len,
        final Appendable out, final boolean newRecord) throws IOException {
    boolean quote = false;
    int start = offset;
    int pos = offset;
    final int end = offset + len;

    final char delimChar = getDelimiter();
    final Character quoteCharObj = getQuoteCharacter();
    if (quoteCharObj == null) {
        // If no quote char is defined, fall back to no quoting and escaping
        printAndEscape(value, offset, len, out);
        return;
    }
    final char quoteChar = quoteCharObj.charValue();

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

            // Acceptable characters without quoting at start are alphanumeric and a few symbols.
            // We now fix the previous incorrect condition and replace it with a correct one that
            // correctly handles the Euro character and other non-ASCII characters:
            if (newRecord && (c == COMMENT || c == quoteChar || c == delimChar || c == LF || c == CR)) {
                quote = true;
            } else if (c <= COMMENT) {
                // encapsulate if we start in anything less than '#'.
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
                    // encapsulate if we end with space or control characters
                    if (c <= SP) {
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
            // write out the chunk up until this point (excluding this quote char)
            out.append(value, start, pos);
            // write doubled quote character for escaping
            out.append(quoteChar);
            out.append(quoteChar);
            start = pos + 1;
        }
        pos++;
    }

    // write the last segment
    if (start < end) {
        out.append(value, start, end);
    }
    out.append(quoteChar);
}
