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
                end = Integer.MAX_VALUE;
            } else {
                end = 'z' + 1;
                start = ' ';
            }
        }
    }

    int gap = end - start;

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
            if (ch >= 56320 && ch <= 57343) {
                if (pos + 1 == count) {
                    continue;
                }
                // low surrogate, insert high surrogate after putting it in
                buffer[pos++] = ch;
                buffer[pos++] = (char) (55296 + random.nextInt(128));
            } else if (ch >= 55296 && ch <= 56191) {
                if (pos + 1 == count) {
                    continue;
                }
                // high surrogate, insert low surrogate before putting it in
                buffer[pos++] = (char) (56320 + random.nextInt(128));
                buffer[pos++] = ch;
            } else if (ch >= 56192 && ch <= 56319) {
                // private high surrogate, skip it
                continue;
            } else {
                buffer[pos++] = ch;
            }
        }
    }
    return new String(buffer);
}
