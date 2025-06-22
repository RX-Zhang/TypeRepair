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
            // Need to check if milliseconds present; if not, insert '.000' before timezone offset
            c = dateStr.charAt(len-9);
            if (Character.isDigit(c)) {
                // check if there is a dot before timezone part
                boolean hasMillis = false;
                for (int i = len - 9; i < len; ++i) {
                    if (dateStr.charAt(i) == '.') {
                        hasMillis = true;
                        break;
                    }
                }
                if (!hasMillis) {
                    StringBuilder sb = new StringBuilder(dateStr);
                    sb.insert(len-5, ".000");
                    dateStr = sb.toString();
                    len = dateStr.length();
                }
            }
            if (_formatISO8601 == null) {
                df = _formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601, DATE_FORMAT_STR_ISO8601, _timezone, _locale);
            } else {
                df = _formatISO8601;
            }
        } else {
            // If not, plain date. Easiest to just patch 'Z' in the end?
            StringBuilder sb = new StringBuilder(dateStr);
            // And possibly also millisecond part if missing
            int timeStart = dateStr.indexOf('T');
            int timeLen = (timeStart < 0) ? 0 : len - timeStart - 1;
            if (timeLen > 0 && timeLen <= 8) {
                sb.append(".000");
            }
            sb.append('Z');
            dateStr = sb.toString();
            if (_formatISO8601_z == null) {
                df = _formatISO8601_z = _cloneFormat(DATE_FORMAT_ISO8601_Z, DATE_FORMAT_STR_ISO8601_Z,
                        _timezone, _locale);
            } else {
                df = _formatISO8601_z;
            }
        }
    }
    return df.parse(dateStr, pos);
}
