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
            // Adjust length in case we modify dateStr
            // Remove optional colon in timezone offset: "08:00" -> "0800"
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
            // Check if millis are missing between seconds and timezone offset
            // The character at len-9 should be ':' if seconds exist, digit if not

            // First ensure len is enough to check len-9 safely:
            if (len > 9) {
                c = dateStr.charAt(len - 9);
                if (c == 'T') { // no seconds at all, so insert ".000" after minutes
                    StringBuilder sb = new StringBuilder(dateStr);
                    sb.insert(len - 6, ":00.000");
                    dateStr = sb.toString();
                    len = dateStr.length();
                } else if (Character.isDigit(c)) {
                    // A digit here means seconds are present but millis might be missing
                    boolean hasMillis = false;
                    int dotPos = dateStr.indexOf('.', len - 9);
                    if (dotPos != -1 && dotPos < len - 5) { // some minimal check
                        hasMillis = true;
                    }
                    if (!hasMillis) {
                        StringBuilder sb = new StringBuilder(dateStr);
                        sb.insert(len - 5, ".000");
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
            // And possible also millisecond part if missing
            int timeIndex = dateStr.lastIndexOf('T');
            int timeLen = (timeIndex >= 0) ? (len - timeIndex - 1) : 0;
            if (timeLen > 0 && timeLen <= 5) { // Missing seconds and millis
                sb.append(":00.000");
            } else if (timeLen > 5 && timeLen <= 8) { // Missing millis only
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
