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

    if (end <= start) {
        throw new IllegalArgumentException("Parameter end (" + end + ") must be greater than start (" + start + ")");
    }

    char[] buffer = new char[count];
    int gap = end - start;
    int countCopy = count;

    while (countCopy-- != 0) {
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
                if(countCopy == 0) {
                    countCopy++;
                } else {
                    // low surrogate, insert high surrogate after putting it in
                    buffer[countCopy] = ch;
                    countCopy--;
                    buffer[countCopy] = (char) (55296 + random.nextInt(128));
                }
            } else if(ch >= 55296 && ch <= 56191) {
                if(countCopy == 0) {
                    countCopy++;
                } else {
                    // high surrogate, insert low surrogate before putting it in
                    buffer[countCopy] = (char) (56320 + random.nextInt(128));
                    countCopy--;
                    buffer[countCopy] = ch;
                }
            } else if(ch >= 56192 && ch <= 56319) {
                // private high surrogate, skip it
                countCopy++;
            } else {
                buffer[countCopy] = ch;
            }
        } else {
            countCopy++;
        }
    }
    return new String(buffer);
}
