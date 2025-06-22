public static long safeMultiply(long val1, int val2) {
    switch (val2) {
        case -1:
            return -val1;
        case 0:
            return 0L;
        case 1:
            return val1;
    }
    long total = val1 * val2;
    if (total / val2 != val1 && val2 != 0) { // Added check for val2 != 0 to avoid division by zero
      throw new ArithmeticException("Multiplication overflows a long: " + val1 + " * " + val2);
    }
    return total;
}
