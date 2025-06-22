public static float max(final float a, final float b) {
    if (Float.isNaN(a)) {
        return Float.isNaN(b) ? Float.NaN : b;
    }
    if (Float.isNaN(b)) {
        return a;
    }
    return (a <= b) ? b : a;
}
