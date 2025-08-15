public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    // Instead of checking only if the first byte is 0 and returning 0,
    // handle the case where the entire field is zero or space filled
    boolean allNulsOrSpaces = true;
    for (int i = offset; i < offset + length; i++) {
        if (buffer[i] != 0 && buffer[i] != ' ') {
            allNulsOrSpaces = false;
            break;
        }
    }
    if (allNulsOrSpaces) {
        return 0L;
    }

    // Skip leading spaces
    while (start < end){
        if (buffer[start] == ' '){
            start++;
        } else {
            break;
        }
    }

    // Must have trailing NUL or space
    byte trailer;
    trailer = buffer[end-1];
    if (trailer == 0 || trailer == ' '){
        end--;
    } else {
        throw new IllegalArgumentException(
                exceptionMessage(buffer, offset, length, end-1, trailer));
    }
    // May have additional NULs or spaces
    trailer = buffer[end - 1];
    if (trailer == 0 || trailer == ' '){
        end--;
    }

    for ( ;start < end; start++) {
        final byte currentByte = buffer[start];
        // CheckStyle:MagicNumber OFF
        if (currentByte < '0' || currentByte > '7'){
            throw new IllegalArgumentException(
                    exceptionMessage(buffer, offset, length, start, currentByte));
        }
        result = (result << 3) + (currentByte - '0'); // convert from ASCII
        // CheckStyle:MagicNumber ON
    }

    return result;
}
