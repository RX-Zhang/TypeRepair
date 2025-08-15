public static String random(int count, int start, int end, boolean letters, boolean numbers,
                            char[] chars, Random random) {
    if (count == 0) {
        return "";
    } else if (count < 0) {
        throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
    }
    if (chars != null && chars.length == 0) {
        throw new IllegalArgumentException("The chars array must not be empty");
    }

    if (start == 0 && end == 0) {
        if (chars != null) {
            end = chars.length;
        } else {
            if (!letters && !numbers) {
                start = 0;
                end = Integer.MAX_VALUE;
            } else {
                start = ' ';
                end = 'z' + 1;
            }
        }
    }

    int gap = (chars == null) ? end - start : chars.length;

    char[] buffer = new char[count];
    int pos = 0;

    while (pos < count) {
        char ch;
        if (chars == null) {
            ch = (char) (random.nextInt(gap) + start);
        } else {
            ch = chars[random.nextInt(gap)];
        }
        if (letters && Character.isLetter(ch)
                || numbers && Character.isDigit(ch)
                || !letters && !numbers) {
            // handle surrogate pairs properly
            if (ch >= 56320 && ch <= 57343) {
                // low surrogate range
                if (pos == count - 1) {
                    continue; // no room for high surrogate, skip char
                }
                buffer[pos++] = ch;
                buffer[pos++] = (char) (55296 + random.nextInt(128));
            } else if (ch >= 55296 && ch <= 56191) {
                // high surrogate range
                if (pos == count - 1) {
                    continue; // no room for low surrogate, skip char
                }
                buffer[pos++] = (char) (56320 + random.nextInt(128));
                buffer[pos++] = ch;
            } else if (ch >= 56192 && ch <= 56319) {
                // private high surrogate, skip
                continue;
            } else {
                buffer[pos++] = ch;
            }
        }
    }
    return new String(buffer, 0, pos);
}
