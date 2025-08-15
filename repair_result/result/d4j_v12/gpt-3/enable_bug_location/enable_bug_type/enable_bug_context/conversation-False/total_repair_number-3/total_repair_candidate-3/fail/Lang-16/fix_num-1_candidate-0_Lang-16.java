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
    String strCopy = str.trim();
    boolean isHex = strCopy.startsWith("0x") || strCopy.startsWith("0X") || strCopy.startsWith("-0x") || strCopy.startsWith("-0X");
    if (isHex) {
        // Use the right parsing for hex numbers, parse as Integer or Long or BigInteger depending on length and sign
        int hexStart = (strCopy.startsWith("-")) ? 3 : 2; // skip "-0x" or "0x"
        String hexDigits = strCopy.substring(hexStart);
        if (hexDigits.length() == 0) {
            throw new NumberFormatException(str + " is not a valid number.");
        }
        // Validate hex digits
        for (int i = 0; i < hexDigits.length(); i++) {
            char ch = hexDigits.charAt(i);
            boolean validHexDigit = (ch >= '0' && ch <= '9') 
                                 || (ch >= 'a' && ch <= 'f') 
                                 || (ch >= 'A' && ch <= 'F');
            if (!validHexDigit) {
                throw new NumberFormatException(str + " is not a valid number.");
            }
        }

        try {
            // Try Integer first
            return Integer.decode(strCopy);
        } catch (NumberFormatException e) {
            try {
                // Try Long
                return Long.decode(strCopy);
            } catch (NumberFormatException e2) {
                // Else BigInteger
                String biStr = (strCopy.startsWith("-")) ? "-" + hexDigits : hexDigits;
                return new BigInteger(biStr, 16);
            }
        }
    }

    char lastChar = strCopy.charAt(strCopy.length() - 1);
    String mant;
    String dec;
    String exp;
    int decPos = strCopy.indexOf('.');
    int expPosE = strCopy.indexOf('e');
    int expPosEE = strCopy.indexOf('E');
    int expPos = -1;
    if (expPosE > -1 && expPosEE > -1) {
        expPos = Math.min(expPosE, expPosEE);
    } else if (expPosE > -1) {
        expPos = expPosE;
    } else if (expPosEE > -1) {
        expPos = expPosEE;
    }

    if (decPos > -1) {

        if (expPos > -1) {
            if (expPos < decPos || expPos > strCopy.length()) {
                throw new NumberFormatException(strCopy + " is not a valid number.");
            }
            dec = strCopy.substring(decPos + 1, expPos);
        } else {
            dec = strCopy.substring(decPos + 1);
        }
        mant = strCopy.substring(0, decPos);
    } else {
        if (expPos > -1) {
            if (expPos > strCopy.length()) {
                throw new NumberFormatException(strCopy + " is not a valid number.");
            }
            mant = strCopy.substring(0, expPos);
        } else {
            mant = strCopy;
        }
        dec = null;
    }
    if (!Character.isDigit(lastChar) && lastChar != '.') {
        if (expPos > -1 && expPos < strCopy.length() - 1) {
            exp = strCopy.substring(expPos + 1, strCopy.length() - 1);
        } else {
            exp = null;
        }
        //Requesting a specific type..
        String numeric = strCopy.substring(0, strCopy.length() - 1);
        boolean allZeros = isAllZeros(mant) && (exp == null || isAllZeros(exp));
        switch (lastChar) {
            case 'l' :
            case 'L' :
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
                throw new NumberFormatException(strCopy + " is not a valid number.");
            case 'f' :
            case 'F' :
                try {
                    Float f = NumberUtils.createFloat(numeric);
                    if (!(f.isInfinite() || (f.floatValue() == 0.0F && !allZeros))) {
                        //If it's too big for a float or the float value = 0 and the string
                        //has non-zeros in it, then float does not have the precision we want
                        return f;
                    }

                } catch (NumberFormatException nfe) { // NOPMD
                    // ignore the bad number
                }
                //$FALL-THROUGH$
            case 'd' :
            case 'D' :
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
            default :
                throw new NumberFormatException(strCopy + " is not a valid number.");

        }
    } else {
        //User doesn't have a preference on the return type, so let's start
        //small and go from there...
        if (expPos > -1 && expPos < strCopy.length() - 1) {
            exp = strCopy.substring(expPos + 1, strCopy.length());
        } else {
            exp = null;
        }
        if (dec == null && exp == null) {
            //Must be an int,long,bigint
            try {
                return createInteger(strCopy);
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }
            try {
                return createLong(strCopy);
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }
            return createBigInteger(strCopy);

        } else {
            //Must be a float,double,BigDec
            boolean allZeros = isAllZeros(mant) && (exp == null || isAllZeros(exp));
            try {
                Float f = createFloat(strCopy);
                if (!(f.isInfinite() || (f.floatValue() == 0.0F && !allZeros))) {
                    return f;
                }
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }
            try {
                Double d = createDouble(strCopy);
                if (!(d.isInfinite() || (d.doubleValue() == 0.0D && !allZeros))) {
                    return d;
                }
            } catch (NumberFormatException nfe) { // NOPMD
                // ignore the bad number
            }

            return createBigDecimal(strCopy);

        }
    }
}
