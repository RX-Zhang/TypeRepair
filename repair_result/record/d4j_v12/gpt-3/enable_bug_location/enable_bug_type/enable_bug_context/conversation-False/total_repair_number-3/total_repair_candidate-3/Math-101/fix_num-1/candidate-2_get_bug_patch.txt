public Complex parse(String source, ParsePosition pos) {
    int initialIndex = pos.getIndex();

    // parse whitespace
    parseAndIgnoreWhitespace(source, pos);

    // parse real
    Number re = parseNumber(source, getRealFormat(), pos);
    if (re == null) {
        // invalid real number
        pos.setIndex(initialIndex);
        return null;
    }

    // parse sign
    int startIndex = pos.getIndex();
    char c = parseNextCharacter(source, pos);
    int sign = 0;
    switch (c) {
    case 0 :
        // no sign
        return new Complex(re.doubleValue(), 0.0);
    case '-' :
        sign = -1;
        break;
    case '+' :
        sign = 1;
        break;
    default :
        // invalid sign
        pos.setIndex(initialIndex);
        pos.setErrorIndex(startIndex);
        return null;
    }

    // parse whitespace
    parseAndIgnoreWhitespace(source, pos);

    // parse imaginary
    Number im = parseNumber(source, getRealFormat(), pos);
    if (im == null) {
        // invalid imaginary number
        pos.setIndex(initialIndex);
        return null;
    }

    // parse imaginary character
    int n = getImaginaryCharacter().length();
    startIndex = pos.getIndex();
    int endIndex = startIndex + n;
    
    // Add boundary check for endIndex
    if (endIndex > source.length()) {
        pos.setIndex(initialIndex);
        pos.setErrorIndex(startIndex);
        return null;
    }
    
    if (source.substring(startIndex, endIndex).compareTo(getImaginaryCharacter()) != 0) {
        pos.setIndex(initialIndex);
        pos.setErrorIndex(startIndex);
        return null;
    }
    pos.setIndex(endIndex);

    return new Complex(re.doubleValue(), im.doubleValue() * sign);
}
