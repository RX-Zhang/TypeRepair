public static Number createNumber(String str) throws NumberFormatException {
    if (str == null) {
        return null;
    }
    if (StringUtils.isBlank(str)) {
        throw new NumberFormatException("A blank string is not a valid number");
    }
    if (str.startsWith("--")) {
        // this is protection for poorness in java.lang.BigDecimal.
        // it accepts this as a legal value, but it does not appear 
        // to be in specification of class. OS X Java parses it to 
        // a wrong value.
        return null;
    }
    String strTrimmed = str.trim();
    if (strTrimmed.startsWith("0x") || strTrimmed.startsWith("0X") || strTrimmed.startsWith("-0x") || strTrimmed.startsWith("-0X")) {
        // hexadecimal
        return createInteger(strTrimmed);
    }
    char lastChar = strTrimmed.charAt(strTrimmed.length() - 1);
    String mant;
    String dec;
    String exp;
    int decPos = strTrimmed.indexOf('.');
    int ePosLower = strTrimmed.indexOf('e');
    int ePosUpper = strTrimmed.indexOf('E');
    int expPos = (ePosLower > -1) ? ePosLower : ePosUpper;

    if (decPos > -1) {

        if (expPos > -1) {
            if (expPos < decPos || expPos >= strTrimmed.length()) {
                throw new NumberFormatException(strTrimmed + " is not a valid number.");
            }
            dec = strTrimmed.substring(decPos + 1, expPos);
        } else {
            dec = strTrimmed.substring(decPos + 1);
        }
        mant = strTrimmed.substring(0, decPos);
    } else {
        if (expPos > -1) {
            if (expPos >= strTrimmed.length()) {
                throw new NumberFormatException(strTrimmed + " is not a valid number.");
            }
            mant = strTrimmed.substring(0, expPos);
        } else {
            mant = strTrimmed;
        }
        dec = null;
    }
    if (!Character.isDigit(lastChar) && lastChar != '.') {
        if (expPos > -1 && expPos < strTrimmed.length() - 1) {
            exp = strTrimmed.substring(expPos + 1, strTrimmed.length() - 1);
        } else {
            exp = null;
        }
        // Requesting a specific type..
        String numeric = strTrimmed.substring(0, strTrimmed.length() - 1);
        boolean allZeros = isAllZeros(mant) && (exp == null || isAllZeros(exp));
        switch (lastChar) {
            case 'l':
            case 'L':
                if (dec == null
                    && exp == null
                    && ((numeric.charAt(0) == '-' && isDigits(numeric.substring(1))) || isDigits(numeric))) {
                    try {
                        return createLong(numeric);
                    } catch (NumberFormatException nfe) { // NOPMD
                        // Too big for a long
                    }
                    return createBigInteger(numeric);

                }
                throw new NumberFormatException(strTrimmed + " is not a valid number.");
            case 'f':
            case 'F':
                try {
                    Float f = NumberUtils.createFloat(numeric);
                    if (!(f.isInfinite() || (f.floatValue() == 0.0F && !allZeros))) {
                        // If it's too big for a float or the float value = 0 and the string
                        // has non-zeros in it, then float does not have the precision we want
                        return f;
                    }
                } catch (NumberFormatException nfe) { // NOPMD
                    // ignore the bad number
                }
                //$FALL-THROUGH$
            case 'd':
            case 'D':
                try {
                    Double d = NumberUtils.createDouble(numeric);
                    if (!(d.isInfinite() || (d.doubleValue() == 0.0D && !allZeros))) {
                        return d;
                    }
                } catch (NumberFormatException nfe) { // NOPMD
                    // ignore the bad number
                }
                try {
                    return createBigDecimal(numeric);
                } catch (NumberFormatException e) { // NOPMD
                    // ignore the bad number
                }
                //$FALL-THROUGH$
            default:
                throw new NumberFormatException(strTrimmed + " is not a valid number.");
        }
    } else {
        // User doesn't have a preference on the return type, so let's start
        // small and go from there...
        if (expPos > -1 && expPos < strTrimmed.length() - 1) {
            exp = strTrimmed.substring(expPos + 1, strTrimmed.length());
        } else {
            exp = null;
        }
        if (dec == null && exp == null) {
            // Must be an int,long,bigint
            try {
                return createInteger(strTrimmed);
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }
            try {
                return createLong(strTrimmed);
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }
            return createBigInteger(strTrimmed);

        } else {
            // Must be a float,double,BigDec
            boolean allZeros = isAllZeros(mant) && (exp == null || isAllZeros(exp));
            try {
                Float f = createFloat(strTrimmed);
                if (!(f.isInfinite() || (f.floatValue() == 0.0F && !allZeros))) {
                    return f;
                }
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }
            try {
                Double d = createDouble(strTrimmed);
                if (!(d.isInfinite() || (d.doubleValue() == 0.0D && !allZeros))) {
                    return d;
                }
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }

            return createBigDecimal(strTrimmed);

        }
    }
}
