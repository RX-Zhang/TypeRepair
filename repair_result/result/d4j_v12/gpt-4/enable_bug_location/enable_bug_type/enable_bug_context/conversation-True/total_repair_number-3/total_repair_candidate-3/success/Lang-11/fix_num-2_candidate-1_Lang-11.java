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

    int gap = end - start;
    if (gap <= 0) {
        throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
    }

    char[] buffer = new char[count];
    int generated = 0;

    while (generated < count) {
        char ch;
        if (chars == null) {
            ch = (char) (random.nextInt(gap) + start);
        } else {
            ch = chars[random.nextInt(gap) + start];
        }
        if (letters && Character.isLetter(ch)
                || numbers && Character.isDigit(ch)
                || !letters && !numbers) {
            if(ch >= 56320 && ch <= 57343) {
                if (generated + 1 == count) {
                    continue;
                }
                buffer[generated++] = (char) (55296 + random.nextInt(128));
                buffer[generated++] = ch;
            } else if(ch >= 55296 && ch <= 56191) {
                if (generated + 1 == count) {
                    continue;
                }
                buffer[generated++] = ch;
                buffer[generated++] = (char) (56320 + random.nextInt(128));
            } else if(ch >= 56192 && ch <= 56319) {
                // private high surrogate, skip it
                continue;
            } else {
                buffer[generated++] = ch;
            }
        }
    }
    return new String(buffer);
}
