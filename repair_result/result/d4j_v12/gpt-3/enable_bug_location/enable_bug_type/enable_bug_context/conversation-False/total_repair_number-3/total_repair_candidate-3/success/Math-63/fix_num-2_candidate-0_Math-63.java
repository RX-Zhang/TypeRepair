public static boolean equals(double x, double y) {
    // NaN does not equal NaN in this check, so only compare with ==; remove NaN check here
    return x == y;
}
