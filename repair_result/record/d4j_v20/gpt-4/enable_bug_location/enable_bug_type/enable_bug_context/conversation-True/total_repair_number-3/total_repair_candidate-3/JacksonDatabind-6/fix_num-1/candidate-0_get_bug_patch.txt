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
            // Updated length needed after possible modification
            len = dateStr.length();

            // Check for missing milliseconds or seconds
            int timeStart = dateStr.indexOf('T');
            int timeZoneOffset = Math.max(dateStr.lastIndexOf('+'), dateStr.lastIndexOf('-'));
            if (timeStart != -1 && timeZoneOffset != -1 && timeZoneOffset > timeStart) {
                String timePortion = dateStr.substring(timeStart + 1, timeZoneOffset);
                // If time portion has no seconds, add ":00"
                if (timePortion.length() == 5) { // hh:mm
                    StringBuilder sb = new StringBuilder(dateStr);
                    sb.insert(timeZoneOffset, ":00");
                    dateStr = sb.toString();
                    len = dateStr.length();
                    timeZoneOffset = Math.max(dateStr.lastIndexOf('+'), dateStr.lastIndexOf('-'));
                }
                // Insert milliseconds if missing
                int dotIndex = dateStr.indexOf('.', timeStart);
                if (dotIndex == -1 || dotIndex > timeZoneOffset) {
                    StringBuilder sb = new StringBuilder(dateStr);
                    sb.insert(timeZoneOffset, ".000");
                    dateStr = sb.toString();
                    len = dateStr.length();
                } else {
                    // Handle partial milliseconds (1 or 2 digits)
                    int millisEnd = timeZoneOffset;
                    int millisStart = dotIndex + 1;
                    int millisLen = millisEnd - millisStart;
                    if (millisLen == 1) { // one digit millis
                        StringBuilder sb = new StringBuilder(dateStr);
                        sb.insert(millisEnd, "00");
                        dateStr = sb.toString();
                        len = dateStr.length();
                    } else if (millisLen == 2) { // two digit millis
                        StringBuilder sb = new StringBuilder(dateStr);
                        sb.insert(millisEnd, "0");
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
            int timeLen = len - dateStr.lastIndexOf('T') - 1;
            if (timeLen <= 8) {
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
