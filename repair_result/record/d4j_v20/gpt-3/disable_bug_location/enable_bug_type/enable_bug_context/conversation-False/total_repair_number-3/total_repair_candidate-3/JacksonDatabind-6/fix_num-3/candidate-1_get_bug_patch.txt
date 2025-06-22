protected Date parseAsISO8601(String dateStr, ParsePosition pos)
{
    /* 21-May-2009, tatu: DateFormat has very strict handling of
     * timezone  modifiers for ISO-8601. So we need to do some scrubbing.
     */

    /* First: do we have "zulu" format ('Z' == "GMT")? If yes, that's
     * quite simple because we already set date format timezone to be
     * GMT, and hence can just strip out 'Z' altogether
     */
    int len = dateStr.length();
    char c = dateStr.charAt(len-1);
    DateFormat df;

    // [JACKSON-200]: need to support "plain" date...
    if (len <= 10 && Character.isDigit(c)) {
        df = _formatPlain;
        if (df == null) {
            df = _formatPlain = _cloneFormat(DATE_FORMAT_PLAIN, DATE_FORMAT_STR_PLAIN, _timezone, _locale);
        }
    } else if (c == 'Z') {
        df = _formatISO8601_z;
        if (df == null) {
            df = _formatISO8601_z = _cloneFormat(DATE_FORMAT_ISO8601_Z, DATE_FORMAT_STR_ISO8601_Z, _timezone, _locale);
        }
        // [JACKSON-334]: may be missing milliseconds... if so, add
        if (len > 4 && dateStr.charAt(len-4) == ':') {
            StringBuilder sb = new StringBuilder(dateStr);
            sb.insert(len-1, ".000");
            dateStr = sb.toString();
        }
    } else {
        // Let's see if we have timezone indicator or not...
        if (hasTimeZone(dateStr)) {
            // Recalculate length in case dateStr changed externally before call? 
            len = dateStr.length();
            c = dateStr.charAt(len-3);

            if (c == ':') { // remove optional colon
                // remove colon
                StringBuilder sb = new StringBuilder(dateStr);
                sb.delete(len-3, len-2);
                dateStr = sb.toString();
                len = dateStr.length();
            } else if (c == '+' || c == '-') { // missing minutes
                // let's just append '00'
                dateStr += "00";
                len = dateStr.length();
            }

            // Now, check if milliseconds are missing or partially present
            // Milliseconds partial or missing; and even seconds are optional
            // The position to check to insert milliseconds depends on format length

            // We'll find position of 'T' (start of time portion)
            int tIndex = dateStr.indexOf('T');
            if (tIndex >= 0) {
                // Calculate expected minimal length for milliseconds insertion
                // We try to insert ".000" if no milliseconds exist
                
                // Look for decimal point in time (for milliseconds)
                int dotIndex = dateStr.indexOf('.', tIndex);
                if (dotIndex < 0) {
                    // No milliseconds - insert before timezone offset
                    // The timezone offset starts at position len-5 (like +/-hhmm), insert before that
                    StringBuilder sb = new StringBuilder(dateStr);
                    sb.insert(len-5, ".000");
                    dateStr = sb.toString();
                    len = dateStr.length();
                } else {
                    // There are milliseconds but might be partial
                    int fracLen = len - (dotIndex + 1) - 5; // 5 is length of timezone offset "+hhmm"
                    if (fracLen < 3) {
                        // Pad milliseconds to 3 digits
                        StringBuilder sb = new StringBuilder(dateStr);
                        for (int i = 0; i < 3 - fracLen; i++) {
                            sb.insert(len - 5, '0');
                        }
                        dateStr = sb.toString();
                        len = dateStr.length();
                    }
                }
            }

            df = _formatISO8601;
            if (_formatISO8601 == null) {
                df = _formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601, DATE_FORMAT_STR_ISO8601, _timezone, _locale);
            }
        } else {
            // If not, plain date. Easiest to just patch 'Z' in the end?
            StringBuilder sb = new StringBuilder(dateStr);
            int tIndex = dateStr.indexOf('T');
            int timeLen = (tIndex < 0) ? 0 : len - tIndex - 1;
            if (timeLen > 0 && timeLen <= 8) {
                sb.append(".000");
            }
            sb.append('Z');
            dateStr = sb.toString();
            df = _formatISO8601_z;
            if (df == null) {
                df = _formatISO8601_z = _cloneFormat(DATE_FORMAT_ISO8601_Z, DATE_FORMAT_STR_ISO8601_Z,
                        _timezone, _locale);
            }
        }
    }
    return df.parse(dateStr, pos);
}
