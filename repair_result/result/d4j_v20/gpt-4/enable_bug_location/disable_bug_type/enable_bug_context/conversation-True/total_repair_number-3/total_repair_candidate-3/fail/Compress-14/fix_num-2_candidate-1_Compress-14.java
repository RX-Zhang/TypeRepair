public static long parseOctal(final byte[] buffer, final int offset, final int length) {
    long    result = 0;
    int     end = offset + length;
    int     start = offset;

    if (length < 2){
        throw new IllegalArgumentException("Length "+length+" must be at least 2");
    }

    boolean allNUL = true;
    for (int i = start; i < end; i++){
        if (buffer[i] != 0){
            allNUL = false;
            break;
        }
    }
    if (allNUL) {
        return 0L;
    }

    // Instead of the original logic, use parseOctalOrBinary to handle binary values
    return parseOctalOrBinary(buffer, offset, length);
}
