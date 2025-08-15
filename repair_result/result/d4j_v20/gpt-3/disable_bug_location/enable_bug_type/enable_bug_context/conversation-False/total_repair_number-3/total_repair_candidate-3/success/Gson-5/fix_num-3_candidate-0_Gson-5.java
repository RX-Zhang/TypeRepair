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

            // extract hours, minutes
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
                        int endOffset = indexOfNonDigit(date, offset);
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
            // Parse timezone offset properly:
            // Expected formats: +HH:mm, +HHmm, +HH

            int idx = offset + 1;
            // Parse hour offset (two digits)
            int tzHour = 0;
            int tzMinute = 0;

            // There must be at least 1 digit (hour)
            int hourEnd = idx + 2 <= date.length() ? idx + 2 : date.length();
            tzHour = parseInt(date, idx, hourEnd);

            idx = hourEnd;

            if (idx < date.length() && date.charAt(idx) == ':') {
                // Format +HH:mm
                idx++;
                int minEnd = idx + 2 <= date.length() ? idx + 2 : date.length();
                tzMinute = parseInt(date, idx, minEnd);
                idx = minEnd;
            } else if (idx + 2 <= date.length()) {
                // Format +HHmm
                tzMinute = parseInt(date, idx, idx + 2);
                idx += 2;
            } else {
                // Format +HH
                tzMinute = 0;
            }

            offset = idx;

            // Validate hour and minute ranges
            if (tzHour < 0 || tzHour > 23) {
                throw new IllegalArgumentException("Invalid time zone hour offset: " + tzHour);
            }
            if (tzMinute < 0 || tzMinute > 59) {
                throw new IllegalArgumentException("Invalid time zone minute offset: " + tzMinute);
            }

            int totalMinutes = tzHour * 60 + tzMinute;
            if (timezoneIndicator == '-') {
                totalMinutes = -totalMinutes;
            }

            // Construct timezone ID
            StringBuilder timezoneId = new StringBuilder("GMT");
            int absHours = Math.abs(totalMinutes / 60);
            int absMinutes = Math.abs(totalMinutes % 60);
            timezoneId.append(timezoneIndicator);
            if (absHours < 10) timezoneId.append('0');
            timezoneId.append(absHours);
            timezoneId.append(':');
            if (absMinutes < 10) timezoneId.append('0');
            timezoneId.append(absMinutes);

            timezone = TimeZone.getTimeZone(timezoneId.toString());

            // Validate timezone ID matches constructed ID ignoring colon differences
            String act = timezone.getID();
            if (!act.equals(timezoneId.toString()) && !act.replace(":", "").equals(timezoneId.toString().replace(":", ""))) {
                throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + timezoneId + " given, resolves to "
                        + timezone.getID());
            }

        } else {
            throw new IndexOutOfBoundsException("Invalid time zone indicator '" + timezoneIndicator + "'");
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
