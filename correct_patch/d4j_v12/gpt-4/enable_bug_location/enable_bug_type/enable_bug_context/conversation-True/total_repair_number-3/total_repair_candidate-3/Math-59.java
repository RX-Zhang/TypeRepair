public static float max(final float a, final float b) {
    if (Float.isNaN(a)) {
        return a;
    }
    if (Float.isNaN(b)) {
        return b;
    }
    return (a >= b) ? a : b;
}
