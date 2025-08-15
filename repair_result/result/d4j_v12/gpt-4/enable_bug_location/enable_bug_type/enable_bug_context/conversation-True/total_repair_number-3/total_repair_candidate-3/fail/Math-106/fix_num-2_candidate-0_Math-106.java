public Fraction parse(String source, ParsePosition pos) {
    // try to parse improper fraction
    Fraction ret = super.parse(source, pos);
    if (ret != null) {
        // Check for invalid minus signs in improper fraction:
        // e.g., "2 -2 / 3" or "2 2 / -3" should fail.
        // We need to validate the parsed fraction string.
        int index = pos.getIndex();
        int errorIndex = pos.getErrorIndex();
        // If errorIndex is set or index < source length means partial parse; ignore here for simplicity.

        // We re-parse source to check for invalid minus after whole number in the source.

        int wholeEndIndex = source.indexOf(' ');
        if (wholeEndIndex > 0 && wholeEndIndex < source.length() - 1) {
            String fractionPart = source.substring(wholeEndIndex + 1).trim();
            if (fractionPart.startsWith("-") || fractionPart.contains(" -")) {
                pos.setIndex(pos.getErrorIndex() > 0 ? pos.getErrorIndex() : wholeEndIndex + 1);
                return null;
            }
        }
        return ret;
    }

    int initialIndex = pos.getIndex();

    // parse whitespace
    parseAndIgnoreWhitespace(source, pos);

    // parse whole
    Number whole = getWholeFormat().parse(source, pos);
    if (whole == null) {
        // invalid integer number
        // set index back to initial, error index should already be set
        // character examined.
        pos.setIndex(initialIndex);
        return null;
    }

    // parse whitespace
    parseAndIgnoreWhitespace(source, pos);

    // parse numerator
    Number num = getNumeratorFormat().parse(source, pos);
    if (num == null) {
        // invalid integer number
        // set index back to initial, error index should already be set
        // character examined.
        pos.setIndex(initialIndex);
        return null;
    }

    // parse '/'
    int startIndex = pos.getIndex();
    char c = parseNextCharacter(source, pos);
    switch (c) {
    case 0 :
        // no '/'
        // return num as a fraction
        return new Fraction(num.intValue(), 1);
    case '/' :
        // found '/', continue parsing denominator
        break;
    default :
        // invalid '/'
        // set index back to initial, error index should be the last
        // character examined.
        pos.setIndex(initialIndex);
        pos.setErrorIndex(startIndex);
        return null;
    }

    // parse whitespace
    parseAndIgnoreWhitespace(source, pos);

    // parse denominator
    Number den = getDenominatorFormat().parse(source, pos);
    if (den == null) {
        // invalid integer number
        // set index back to initial, error index should already be set
        // character examined.
        pos.setIndex(initialIndex);
        return null;
    }

    // Validate minus signs must be leading (denominator and numerator must be positive)
    if (num.intValue() < 0 || den.intValue() < 0) {
        pos.setIndex(initialIndex);
        pos.setErrorIndex(pos.getIndex());
        return null;
    }

    int w = whole.intValue();
    int n = num.intValue();
    int d = den.intValue();
    return new Fraction(((Math.abs(w) * d) + n) * MathUtils.sign(w), d);
}
