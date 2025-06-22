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
        } else if (!letters && !numbers) {
            start = 0;
            end = Integer.MAX_VALUE;
        } else {
            start = ' ';
            end = 'z' + 1;
        }
    }

    int gap = end - start;
    if (chars != null && (start < 0 || end > chars.length)) {
        throw new IndexOutOfBoundsException("start and end indexes must be valid for chars array");
    }

    char[] buffer = new char[count];

    int remaining = count;
    while (remaining-- != 0) {
        char ch;
        if (chars == null) {
            ch = (char) (random.nextInt(gap) + start);
        } else {
            ch = chars[random.nextInt(gap) + start];
        }

        if (letters && Character.isLetter(ch)
                || numbers && Character.isDigit(ch)
                || !letters && !numbers) {
            if (ch >= 56320 && ch <= 57343) {
                if (remaining == 0) {
                    remaining++;
                } else {
                    buffer[remaining] = ch;
                    remaining--;
                    buffer[remaining] = (char) (55296 + random.nextInt(128));
                }
            } else if (ch >= 55296 && ch <= 56191) {
                if (remaining == 0) {
                    remaining++;
                } else {
                    buffer[remaining] = (char) (56320 + random.nextInt(128));
                    remaining--;
                    buffer[remaining] = ch;
                }
            } else if (ch >= 56192 && ch <= 56319) {
                remaining++;
            } else {
                buffer[remaining] = ch;
            }
        } else {
            remaining++;
        }
    }
    return new String(buffer);
}
