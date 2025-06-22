public static Date parse(String date, ParsePosition pos) throws ParseException {
    Exception fail = null;
    try {
        int offset = pos.getIndex();

        // extract year
        int year = parseInt(date, offset, offset += 4);
        if (checkOffset(date, offset, '-')) {
            offset += 1;
        }

        // extract month
        int month = parseInt(date, offset, offset += 2);
        if (checkOffset(date, offset, '-')) {
            offset += 1;
        }

        // extract day
        int day = parseInt(date, offset, offset += 2);
        // default time value
        int hour = 0;
        int minutes = 0;
        int seconds = 0;
        int milliseconds = 0; // always use 0 otherwise returned date will include millis of current time

        // if the value has no time component (and no time zone), we are done
        boolean hasT = checkOffset(date, offset, 'T');

        if (!hasT && (date.length() <= offset)) {
            Calendar calendar = new GregorianCalendar(year, month - 1, day);

            pos.setIndex(offset);
            return calendar.getTime();
        }

        if (hasT) {

            // extract hours, minutes, seconds and milliseconds
            hour = parseInt(date, offset += 1, offset += 2);
            if (checkOffset(date, offset, ':')) {
                offset += 1;
            }

            minutes = parseInt(date, offset, offset += 2);
            if (checkOffset(date, offset, ':')) {
                offset += 1;
            }
            // second and milliseconds can be optional
            if (date.length() > offset) {
                char c = date.charAt(offset);
                if (c != 'Z' && c != '+' && c != '-') {
                    seconds = parseInt(date, offset, offset += 2);
                    if (seconds > 59 && seconds < 63) seconds = 59; // truncate up to 3 leap seconds
                    // milliseconds can be optional in the format
                    if (checkOffset(date, offset, '.')) {
                        offset += 1;
                        int endOffset = indexOfNonDigit(date, offset); // assume at least one digit
                        int parseEndOffset = Math.min(endOffset, offset + 3); // parse up to 3 digits
                        int fraction = parseInt(date, offset, parseEndOffset);
                        // compensate for "missing" digits
                        switch (parseEndOffset - offset) { // number of digits parsed
                            case 2:
                                milliseconds = fraction * 10;
                                break;
                            case 1:
                                milliseconds = fraction * 100;
                                break;
                            default:
                                milliseconds = fraction;
                        }
                        offset = endOffset;
                    }
                }
            }
        }

        // extract timezone
        if (date.length() <= offset) {
            throw new IllegalArgumentException("No time zone indicator");
        }

        TimeZone timezone = null;
        char timezoneIndicator = date.charAt(offset);

        if (timezoneIndicator == 'Z') {
            timezone = TIMEZONE_UTC;
            offset += 1;
        } else if (timezoneIndicator == '+' || timezoneIndicator == '-') {
            // timezone offset can be of form +hh, +hhmm, or +hh:mm
            int tzStart = offset;
            offset++; // skip + or -

            // parse hour part (2 digits)
            int tzHourEnd = offset + 2;
            if (date.length() < tzHourEnd) {
                throw new IllegalArgumentException("Invalid time zone indicator: incomplete hour offset");
            }
            // check hour digits
            for (int i = offset; i < tzHourEnd; i++) {
                if (!Character.isDigit(date.charAt(i))) {
                    throw new IllegalArgumentException("Invalid time zone indicator: malformed hour offset");
                }
            }
            offset = tzHourEnd;

            int tzMinute = 0;
            if (date.length() > offset) {
                char c = date.charAt(offset);
                if (c == ':') {
                    // colon separator for minutes, skip and parse 2 digits
                    offset++;
                    int tzMinEnd = offset + 2;
                    if (date.length() < tzMinEnd) {
                        throw new IllegalArgumentException("Invalid time zone indicator: incomplete minute offset");
                    }
                    for (int i = offset; i < tzMinEnd; i++) {
                        if (!Character.isDigit(date.charAt(i))) {
                            throw new IllegalArgumentException("Invalid time zone indicator: malformed minute offset");
                        }
                    }
                    tzMinute = parseInt(date, offset, tzMinEnd);
                    offset = tzMinEnd;
                } else if (Character.isDigit(c)) {
                    // parse minutes without colon (2 digits)
                    int tzMinEnd = offset + 2;
                    if (date.length() < tzMinEnd) {
                        throw new IllegalArgumentException("Invalid time zone indicator: incomplete minute offset");
                    }
                    for (int i = offset; i < tzMinEnd; i++) {
                        if (!Character.isDigit(date.charAt(i))) {
                            throw new IllegalArgumentException("Invalid time zone indicator: malformed minute offset");
                        }
                    }
                    tzMinute = parseInt(date, offset, tzMinEnd);
                    offset = tzMinEnd;
                }
                // else no minutes part
            }

            String timezoneOffset = date.substring(tzStart, offset);

            // When timezone has no minutes, we should append it, valid timezones are, for example: +00:00, +0000 and +00

            // 18-Jun-2015, tatu: Minor simplification, skip offset of "+0000"/"+00:00"
            if ("+0000".equals(timezoneOffset) || "+00:00".equals(timezoneOffset) || "+00".equals(timezoneOffset)
                    || "-0000".equals(timezoneOffset) || "-00:00".equals(timezoneOffset) || "-00".equals(timezoneOffset)) {
                timezone = TIMEZONE_UTC;
            } else {
                // Build GMT offset string with colon if needed
                StringBuilder timezoneIdBuilder = new StringBuilder("GMT");
                timezoneIdBuilder.append(timezoneOffset.charAt(0)); // + or -
                timezoneIdBuilder.append(String.format("%02d", Integer.parseInt(timezoneOffset.substring(1,3))));
                if (timezoneOffset.length() == 3) {
                    // only hours, add ":00"
                    timezoneIdBuilder.append(":00");
                } else if (timezoneOffset.length() == 5 && timezoneOffset.charAt(3) != ':') {
                    // HHMM format -> insert colon between hour and minute
                    timezoneIdBuilder.append(':').append(timezoneOffset.substring(3,5));
                } else if (timezoneOffset.length() == 6) {
                    // already HH:MM format
                    timezoneIdBuilder.append(timezoneOffset.substring(3,6));
                } else if (timezoneOffset.length() > 6) {
                    // Possibly longer, append as is after the hour and colon
                    timezoneIdBuilder.append(timezoneOffset.substring(3));
                }

                String timezoneId = timezoneIdBuilder.toString();

                timezone = TimeZone.getTimeZone(timezoneId);

                String act = timezone.getID();
                if (!act.equals(timezoneId)) {
                    /* 22-Jan-2015, tatu: Looks like canonical version has colons, but we may be given
                     *    one without. If so, don't sweat.
                     *   Yes, very inefficient. Hopefully not hit often.
                     *   If it becomes a perf problem, add 'loose' comparison instead.
                     */
                    String cleaned = act.replace(":", "");
                    if (!cleaned.equals(timezoneId.replace(":", ""))) {
                        throw new IndexOutOfBoundsException("Mismatching time zone indicator: "+timezoneId+" given, resolves to "
                                +timezone.getID());
                    }
                }
            }
        } else {
            throw new IndexOutOfBoundsException("Invalid time zone indicator '" + timezoneIndicator+"'");
        }

        Calendar calendar = new GregorianCalendar(timezone);
        calendar.setLenient(false);
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minutes);
        calendar.set(Calendar.SECOND, seconds);
        calendar.set(Calendar.MILLISECOND, milliseconds);

        pos.setIndex(offset);
        return calendar.getTime();
        // If we get a ParseException it'll already have the right message/offset.
        // Other exception types can convert here.
    } catch (IndexOutOfBoundsException e) {
        fail = e;
    } catch (NumberFormatException e) {
        fail = e;
    } catch (IllegalArgumentException e) {
        fail = e;
    }
    String input = (date == null) ? null : ('"' + date + '"');
    String msg = fail.getMessage();
    if (msg == null || msg.isEmpty()) {
        msg = "(" + fail.getClass().getName() + ")";
    }
    ParseException ex = new ParseException("Failed to parse date [" + input + "]: " + msg, pos.getIndex());
    ex.initCause(fail);
    throw ex;
}
