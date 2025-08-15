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
        if (dateStr.charAt(len-4) == ':') {
            StringBuilder sb = new StringBuilder(dateStr);
            sb.insert(len-1, ".000");
            dateStr = sb.toString();
        }
    } else {
        // Let's see if we have timezone indicator or not...
        if (hasTimeZone(dateStr)) {
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
            // Milliseconds partial or missing; and even seconds are optional
            // Check if milliseconds missing before timezone offset
            // We expect: date string format with timezone offset of length 5 (e.g. +hhmm)
            // so position to insert .000 is len-5
            c = dateStr.charAt(len-5);
            if (c == '+' || c == '-') {
                // timezone starts exactly here, so insert milliseconds before that
                StringBuilder sb = new StringBuilder(dateStr);
                // Insert .000 if missing
                // We detect if there is already a '.' before timezone
                int tIndex = len - 5;
                if (dateStr.indexOf(".", tIndex - 4) == -1) {
                    sb.insert(tIndex, ".000");
                    dateStr = sb.toString();
                    len = dateStr.length();
                }
            } else if (Character.isDigit(c)) {
                StringBuilder sb = new StringBuilder(dateStr);
                sb.insert(len-5, ".000");
                dateStr = sb.toString();
                len = dateStr.length();
            }
            df = _formatISO8601;
            if (_formatISO8601 == null) {
                df = _formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601, DATE_FORMAT_STR_ISO8601, _timezone, _locale);
            }
        } else {
            // If no timezone, treat as ISO8601 with Z appended and possibly milliseconds
            StringBuilder sb = new StringBuilder(dateStr);
            int timeIndex = dateStr.indexOf('T');
            int timeLen = (timeIndex < 0) ? 0 : (len - timeIndex - 1);
            if (timeLen > 0 && timeLen <= 8 && dateStr.indexOf('.') == -1) {
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
