public static float max(final float a, final float b) {
    if (Float.isNaN(a)) {
        return b;
    }
    if (Float.isNaN(b)) {
        return a;
    }
    return (a <= b) ? b : a;
}
