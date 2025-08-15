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
            // Check if milliseconds are missing before timezone offset
            // timezone offset length is always 5 (e.g. ±hhmm)
            // So we look at char before timezone offset
            // change from indexing len-9 to len-8 to correctly detect missing millis or seconds
            int millisInsertPos = len - 5;
            boolean needMillis = false;
            // Check if char before millisInsertPos is digit or not
            if (millisInsertPos - 4 >= 0) {
                char ch = dateStr.charAt(millisInsertPos - 4);
                if (ch == 'T') { // Seconds missing
                    needMillis = true;
                } else {
                    // Only insert millis if dot is missing
                    if (dateStr.indexOf('.', millisInsertPos - 4) < 0) {
                        needMillis = true;
                    }
                }
            } else {
                // Defensive fallback: always insert .000 if no seconds/millis
                needMillis = true;
            }

            if (needMillis) {
                StringBuilder sb = new StringBuilder(dateStr);
                sb.insert(millisInsertPos, ".000");
                dateStr = sb.toString();
                len = dateStr.length();
            }

            df = _formatISO8601;
            if (_formatISO8601 == null) {
                df = _formatISO8601 = _cloneFormat(DATE_FORMAT_ISO8601, DATE_FORMAT_STR_ISO8601, _timezone, _locale);
            }
        } else {
            // If not, plain date. Easiest to just patch 'Z' in the end?
            StringBuilder sb = new StringBuilder(dateStr);
            // And possible also millisecond part if missing
            int timeLen = len - dateStr.lastIndexOf('T') - 1;
            if (timeLen <= 5) {
                // time is likely missing seconds and millis: add seconds and millis
                sb.append(":00.000");
            } else if (timeLen <= 8) {
                // seconds are present but millis missing
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
